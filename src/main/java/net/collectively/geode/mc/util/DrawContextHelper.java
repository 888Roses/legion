package net.collectively.geode.mc.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * Collection of {@link DrawContext} utilities.
 */
@SuppressWarnings("unused")
public interface DrawContextHelper {
    /**
     * Draws a UI text with a full outline.
     *
     * @param drawContext  The {@link DrawContext} used to draw the text.
     * @param textRenderer The {@link TextRenderer} used to draw the text. If null, the {@link MinecraftClient#textRenderer} will be used instead.
     * @param text         The {@link Text} to be drawn.
     * @param x            The X position offset of the drawn text.
     * @param y            The Y position offset of the drawn text;
     * @param color        The color of the drawn text.
     * @param outlineColor The color of the outline of the drawn text.
     * @apiNote <b>Expensive:</b> Calls the {@link DrawContext#drawText(TextRenderer, Text, int, int, int, boolean)} method 5 times in total.
     */
    static void drawTextWithOutline(DrawContext drawContext, @Nullable TextRenderer textRenderer, Text text, int x, int y, int color, int outlineColor) {
        if (textRenderer == null) {
            textRenderer = MinecraftClient.getInstance().textRenderer;
        }

        // Outline
        final var outlineText = text.copy().withColor(outlineColor);
        drawContext.drawText(textRenderer, outlineText, x + 1, y, outlineColor, false);
        drawContext.drawText(textRenderer, outlineText, x + 1, y + 1, outlineColor, false);
        drawContext.drawText(textRenderer, outlineText, x + 1, y - 1, outlineColor, false);
        drawContext.drawText(textRenderer, outlineText, x - 1, y, outlineColor, false);
        drawContext.drawText(textRenderer, outlineText, x - 1, y + 1, outlineColor, false);
        drawContext.drawText(textRenderer, outlineText, x - 1, y - 1, outlineColor, false);
        drawContext.drawText(textRenderer, outlineText, x, y + 1, outlineColor, false);
        drawContext.drawText(textRenderer, outlineText, x, y - 1, outlineColor, false);

        // Text
        drawContext.drawText(textRenderer, text, x, y, color, false);
    }

    /**
     * Draws a UI text with a full outline.
     *
     * @param drawContext  The {@link DrawContext} used to draw the text.
     * @param textRenderer The {@link TextRenderer} used to draw the text. If null, the {@link MinecraftClient#textRenderer} will be used instead.
     * @param text         The {@link String} to be drawn.
     * @param x            The X position offset of the drawn text.
     * @param y            The Y position offset of the drawn text;
     * @param color        The color of the drawn text.
     * @param outlineColor The color of the outline of the drawn text.
     * @apiNote <b>Expensive:</b> Calls the {@link DrawContext#drawText(TextRenderer, String, int, int, int, boolean)} method 5 times in total.
     */
    static void drawTextWithOutline(DrawContext drawContext, @Nullable TextRenderer textRenderer, String text, int x, int y, int color, int outlineColor) {
        drawTextWithOutline(drawContext, textRenderer, Text.literal(text), x, y, color, outlineColor);
    }
}
