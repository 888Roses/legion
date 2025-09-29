package net.rose.legion.client.tooltip;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.rose.legion.common.tooltip.PotionTooltipData;
import net.rose.legion.fundation.util.RomanNumber;

import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
public class PotionTooltipComponent implements TooltipComponent {
    private final PotionTooltipData tooltipData;
    private final RenderableStatusEffect[] renderableStatusEffects;

    public PotionTooltipComponent(
            PotionTooltipData tooltipData
    ) {
        this.tooltipData = tooltipData;

        this.renderableStatusEffects = this.tooltipData
                .getEffects()
                .stream()
                .map(instance -> RenderableStatusEffect.fromInstance(
                        instance,
                        this.tooltipData.getDurationMultiplier()
                ))
                .toArray(RenderableStatusEffect[]::new);
    }

    @Override
    public int getHeight() {
        return (18 + 2) * this.renderableStatusEffects.length + 4 - 1;
    }

    @Override
    public int getWidth(
            TextRenderer textRenderer
    ) {
        var width = 18 + 2 + 4;

        return width + Math.max(
                Arrays.stream(this.renderableStatusEffects)
                        .map(this::getPotionStatsText)
                        .map(text -> textRenderer.getWidth(text.asOrderedText()))
                        .max(Integer::compare)
                        .orElse(0),
                Arrays.stream(this.renderableStatusEffects)
                        .map(this::getNameText)
                        .map(text -> textRenderer.getWidth(text.asOrderedText()))
                        .max(Integer::compare)
                        .orElse(0)
        );
    }

    private Text getNameText(
            RenderableStatusEffect renderable
    ) {
        final var name = Text.translatable(renderable.effect().getTranslationKey());
        final var level = Text.literal(" " + RomanNumber.toRoman(renderable.level() + 1));
        return (name.append(level)).styled(style -> style.withColor(renderable.effect().getColor()));
    }

    @Override
    public void drawItems(
            TextRenderer textRenderer,
            int x,
            int y,
            DrawContext context
    ) {
        var stackHeight = -1;

        for (var renderable : this.renderableStatusEffects) {
            context.drawSprite(
                    x + 2,
                    y + 2 + stackHeight,
                    0,
                    18,
                    18,
                    renderable.sprite()
            );

            context.drawText(
                    textRenderer,
                    this.getNameText(renderable),
                    x + 18 + 2 + 4,
                    y + 2 + stackHeight,
                    0xFFFFFFFF,
                    false
            );

            var bottomText = this.getPotionStatsText(renderable);

            context.drawText(
                    textRenderer,
                    bottomText.formatted(Formatting.GRAY),
                    x + 18 + 2 + 4,
                    y + 2 + stackHeight + textRenderer.fontHeight,
                    0xFFFFFFFF,
                    false
            );

            stackHeight += 18 + 2;
        }
    }

    private MutableText getPotionStatsText(RenderableStatusEffect effect) {
        var text = effect.durationText().copy().append(" ");

        final var attributeModifiers = Lists.<Pair<EntityAttribute, EntityAttributeModifier>>newArrayList();
        final var map = effect.effect().getAttributeModifiers();
        if (!map.isEmpty()) {
            for (var entityAttributeEntityAttributeModifierEntry : map.entrySet()) {
                final var modifier = entityAttributeEntityAttributeModifierEntry.getValue();
                final var value = new EntityAttributeModifier(
                        modifier.getName(),
                        effect.effect().adjustModifierAmount(
                                effect.level(),
                                modifier
                        ),
                        modifier.getOperation()
                );

                attributeModifiers.add(new Pair<>(
                        entityAttributeEntityAttributeModifierEntry.getKey(),
                        value
                ));
            }
        }

        if (!attributeModifiers.isEmpty()) {
            for (var pair : attributeModifiers) {
                final var entityAttributeModifier = pair.getSecond();
                double value = entityAttributeModifier.getValue();
                double effectiveValue;
                if (entityAttributeModifier.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_BASE
                        && entityAttributeModifier.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_TOTAL
                ) {
                    effectiveValue = entityAttributeModifier.getValue();
                }
                else {
                    effectiveValue = entityAttributeModifier.getValue() * 100.0;
                }

                if (value > 0.0) {
                    text.append(
                            Text.translatable(
                                    "attribute.modifier.plus." + entityAttributeModifier.getOperation().getId(),
                                    ItemStack.MODIFIER_FORMAT.format(effectiveValue),
                                    Text.translatable(pair.getFirst().getTranslationKey())
                            ).formatted(Formatting.GREEN)
                    );
                }
                else if (value < 0.0) {
                    effectiveValue *= -1.0;
                    text.append(
                            Text.translatable(
                                    "attribute.modifier.take." + entityAttributeModifier.getOperation().getId(),
                                    ItemStack.MODIFIER_FORMAT.format(effectiveValue),
                                    Text.translatable(pair.getFirst().getTranslationKey())
                            ).formatted(Formatting.RED)
                    );
                }

                text = text.append(" ");
            }
        }

        return text;
    }

    private record RenderableStatusEffect(
            StatusEffect effect,
            Text durationText,
            int level,
            Sprite sprite
    ) {
        public static RenderableStatusEffect fromInstance(
                StatusEffectInstance instance,
                double durationMultiplier
        ) {
            return new RenderableStatusEffect(
                    instance.getEffectType(),
                    StatusEffectUtil.getDurationText(
                            instance,
                            (float) durationMultiplier
                    ),
                    instance.getAmplifier(),
                    MinecraftClient
                            .getInstance()
                            .getStatusEffectSpriteManager()
                            .getSprite(instance.getEffectType())
            );
        }
    }
}
