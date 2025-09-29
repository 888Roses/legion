package net.rose.legion.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.rose.legion.fundation.util.OrderedTextConverter;
import org.spongepowered.asm.mixin.*;

import java.util.List;
import java.util.Objects;

@Mixin(
        ChatHud.class
)
public abstract class ChatHudMixin {
    @Shadow
    protected abstract boolean isChatHidden();

    @Shadow
    public abstract int getVisibleLineCount();

    @Shadow
    @Final
    private List<ChatHudLine.Visible> visibleMessages;

    @Shadow
    protected abstract boolean isChatFocused();

    @Shadow
    public abstract double getChatScale();

    @Shadow
    public abstract int getWidth();

    @Shadow
    protected abstract int getMessageIndex(double chatLineX, double chatLineY);

    @Shadow
    protected abstract double toChatLineX(double x);

    @Shadow
    protected abstract double toChatLineY(double y);

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract int getLineHeight();

    @Shadow
    private int scrolledLines;

    @Shadow
    protected abstract int getIndicatorX(
            ChatHudLine.Visible line
    );

    @Shadow
    protected abstract void drawIndicatorIcon(
            DrawContext context,
            int x,
            int y,
            MessageIndicator.Icon icon
    );

    @Shadow
    private boolean hasUnreadNewMessages;

    @Unique
    private static double getMessageOpacityMultiplier(
            int age
    ) {
        var d = (double) age / 200.0;
        d = 1.0 - d;
        d *= 10.0;
        d = MathHelper.clamp(d, 0.0, 1.0);
        d *= d;
        return d;
    }

    /**
     * @author Rosenoire
     * @reason Chat lines enhancements.
     */
    @SuppressWarnings({"ReassignedVariable", "SuspiciousNameCombination"})
    @Overwrite
    public void render(
            DrawContext context,
            int currentTick,
            int mouseX,
            int mouseY
    ) {
        if (!this.isChatHidden()) {
            final var visibleLineCount = this.getVisibleLineCount();
            final var visibleMessageCount = this.visibleMessages.size();

            if (visibleMessageCount > 0) {
                final var chatFocused = this.isChatFocused();
                final var chatScale = (float) this.getChatScale();
                final var trueWidth = MathHelper.ceil((float) this.getWidth() / chatScale);
                final var windowHeight = context.getScaledWindowHeight();

                context.getMatrices().push();
                context.getMatrices().scale(chatScale, chatScale, 1.0F);
                context.getMatrices().translate(4.0F, 0.0F, 0.0F);

                final var trueHeight = MathHelper.floor((float) (windowHeight - 40) / chatScale);
                final var hoveredMessageIndex = this.getMessageIndex(this.toChatLineX(mouseX), this.toChatLineY(mouseY));
                final var chatOpacity = this.client.options.getChatOpacity().getValue() * 0.9 + 0.1;

                final var optionBackgroundOpacity = this.client.options.getTextBackgroundOpacity().getValue();
                final var optionLineSpacing = this.client.options.getChatLineSpacing().getValue();

                final var lineHeight = this.getLineHeight();
                final var effectiveLineSpacing = (int) Math.round(-8.0 * (optionLineSpacing + 1.0) + 4.0 * optionLineSpacing);
                var q = 0;

                int messageTickTime;
                int messageTextOpacity;
                int messageBackgroundOpacity;
                int textY;

                for (var r = 0; r + this.scrolledLines < this.visibleMessages.size() && r < visibleLineCount; ++r) {
                    final var visibleMessageIndex = r + this.scrolledLines;
                    final var visibleMessage = this.visibleMessages.get(visibleMessageIndex);

                    if (visibleMessage == null) {
                        continue;
                    }

                    messageTickTime = currentTick - visibleMessage.addedTime();

                    if (messageTickTime < 200 || chatFocused) {
                        final var messageOpacity = chatFocused ? 1.0 : getMessageOpacityMultiplier(messageTickTime);
                        messageTextOpacity = (int) (255.0 * messageOpacity * chatOpacity);
                        messageBackgroundOpacity = (int) (255.0 * messageOpacity * optionBackgroundOpacity);
                        ++q;

                        if (messageTextOpacity > 3) {
                            textY = trueHeight - r * lineHeight;
                            final var effectiveTextY = textY + effectiveLineSpacing;

                            var backgroundColour = messageBackgroundOpacity << 24;
                            if (this.client.player != null) {
                                final var converter = new OrderedTextConverter();
                                visibleMessage.content().accept(converter);
                                final var content = converter.getText().getString();
                                if (content.contains(">") && content.split(">")[1].contains(this.client.player.getEntityName())) {
                                    backgroundColour = ColorHelper.Argb.getArgb(
                                            messageBackgroundOpacity,
                                            255,
                                            85,
                                            255
                                    );
                                }
                            }

                            context.getMatrices().push();
                            context.getMatrices().translate(0.0F, 0.0F, 50.0F);
                            context.fill(
                                    -4,
                                    textY - lineHeight,
                                    trueWidth + 4 + 4,
                                    textY,
                                    backgroundColour
                            );

                            final var messageIndicator = visibleMessage.indicator();
                            if (messageIndicator != null) {
                                final var textOpacity = messageIndicator.indicatorColor() | messageTextOpacity << 24;

                                context.fill(
                                        -4,
                                        textY - lineHeight,
                                        -2,
                                        textY,
                                        textOpacity
                                );

                                if (visibleMessageIndex == hoveredMessageIndex && messageIndicator.icon() != null) {
                                    final var indicatorX = this.getIndicatorX(visibleMessage);
                                    Objects.requireNonNull(this.client.textRenderer);
                                    final var indicatorY = effectiveTextY + 9;

                                    this.drawIndicatorIcon(
                                            context,
                                            indicatorX,
                                            indicatorY,
                                            messageIndicator.icon()
                                    );
                                }
                            }

                            context.getMatrices().translate(0.0F, 0.0F, 50.0F);
                            context.drawTextWithShadow(this.client.textRenderer, visibleMessage.content(), 0, effectiveTextY, 16777215 + (messageTextOpacity << 24));
                            context.getMatrices().pop();
                        }
                    }
                }

                final var unprocessedMessageCount = this.client.getMessageHandler().getUnprocessedMessageCount();
                var halfOpacity = 0;
                if (unprocessedMessageCount > 0) {
                    halfOpacity = (int) (128.0 * chatOpacity);
                    messageTickTime = (int) (255.0 * optionBackgroundOpacity);

                    context.getMatrices().push();
                    context.getMatrices().translate(0.0F, (float) trueHeight, 50.0F);

                    context.fill(
                            -2,
                            0,
                            trueWidth + 4,
                            9,
                            messageTickTime << 24
                    );

                    context.getMatrices().translate(0.0F, 0.0F, 50.0F);

                    context.drawTextWithShadow(
                            this.client.textRenderer,
                            Text.translatable("chat.queue", unprocessedMessageCount),
                            0,
                            1,
                            16777215 + (halfOpacity << 24)
                    );

                    context.getMatrices().pop();
                }

                if (chatFocused) {
                    halfOpacity = this.getLineHeight();
                    messageTickTime = visibleMessageCount * halfOpacity;

                    final var ae = q * halfOpacity;
                    final var af = this.scrolledLines * ae / visibleMessageCount - trueHeight;
                    messageTextOpacity = ae * ae / messageTickTime;

                    if (messageTickTime != ae) {
                        messageBackgroundOpacity = af > 0 ? 170 : 96;
                        var w = this.hasUnreadNewMessages ? 13382451 : 3355562;
                        textY = trueWidth + 4;
                        context.fill(
                                textY,
                                -af,
                                textY + 2,
                                -af - messageTextOpacity,
                                w + (messageBackgroundOpacity << 24)
                        );

                        context.fill(
                                textY + 2,
                                -af,
                                textY + 1,
                                -af - messageTextOpacity,
                                13421772 + (messageBackgroundOpacity << 24)
                        );
                    }
                }

                context.getMatrices().pop();
            }
        }
    }
}
