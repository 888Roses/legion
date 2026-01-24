package net.collectively.geode.mc.util;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class GeodeText {
    private final List<Entry> queue = new ArrayList<>();
    private final Style defaultStyle;

    public GeodeText(Style defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public GeodeText() {
        this(Style.EMPTY);
    }

    public MutableText build() {
        var current = Text.empty();
        for (var i : queue) for (var j : i.text.getWithStyle(i.style)) current.append(j);
        return current;
    }

    public GeodeText literal(Text text) {
        queue.add(new Entry((MutableText) text, defaultStyle));
        return this;
    }

    public GeodeText literal(Object text) {
        return literal(Text.literal(String.valueOf(text)));
    }

    public GeodeText literal(Object text, Formatting formatting) {
        return literal(text).withFormatting(formatting);
    }

    public GeodeText translatable(String key, Object... params) {
        return literal(Text.translatable(key, params));
    }

    public GeodeText modifyStyle(Function<Style, Style> factory) {
        queue.getLast().styled(factory);
        return this;
    }

    public GeodeText setStyle(Style style) {
        queue.getLast().style = style;
        return this;
    }

    /**
     * Returns a new style with the color provided and all other attributes of this style.
     *
     * @param color the new color
     */
    public GeodeText withColor(@Nullable TextColor color) {
        return modifyStyle(style -> style.withColor(color));
    }

    /**
     * Returns a new style with the color provided and all other attributes of this style.
     *
     * @param color the new color
     */
    public GeodeText withColor(@Nullable Formatting color) {
        return modifyStyle(style -> style.withColor(color));
    }

    public GeodeText withColor(int rgbColor) {
        return modifyStyle(style -> style.withColor(rgbColor));
    }

    public GeodeText withShadowColor(int shadowColor) {
        return modifyStyle(style -> style.withShadowColor(shadowColor));
    }

    public GeodeText withoutShadow() {
        return modifyStyle(style -> style.withoutShadow());
    }

    /**
     * Returns a new style with the bold attribute provided and all other attributes of this style.
     */
    public GeodeText withBold(@Nullable Boolean bold) {
        return modifyStyle(style -> style.withBold(bold));
    }

    /**
     * Returns a new style with the italic attribute provided and all other attributes of this style.
     *
     * @param italic the new italic property
     */
    public GeodeText withItalic(@Nullable Boolean italic) {
        return modifyStyle(style -> style.withItalic(italic));
    }

    /**
     * Returns a new style with the underline attribute provided and all other attributes of this style.
     */
    public GeodeText withUnderline(@Nullable Boolean underline) {
        return modifyStyle(style -> style.withUnderline(underline));
    }

    public GeodeText withStrikethrough(@Nullable Boolean strikethrough) {
        return modifyStyle(style -> style.withStrikethrough(strikethrough));
    }

    public GeodeText withObfuscated(@Nullable Boolean obfuscated) {
        return modifyStyle(style -> style.withObfuscated(obfuscated));
    }

    /**
     * Returns a new style with the click event provided and all other attributes of this style.
     *
     * @param clickEvent the new click event
     */
    public GeodeText withClickEvent(@Nullable ClickEvent clickEvent) {
        return modifyStyle(style -> style.withClickEvent(clickEvent));
    }

    /**
     * Returns a new style with the hover event provided and all other attributes of this style.
     *
     * @param hoverEvent the new hover event
     */
    public GeodeText withHoverEvent(@Nullable HoverEvent hoverEvent) {
        return modifyStyle(style -> style.withHoverEvent(hoverEvent));
    }

    /**
     * Returns a new style with the insertion provided and all other attributes of this style.
     *
     * @param insertion the new insertion string
     */
    public GeodeText withInsertion(@Nullable String insertion) {
        return modifyStyle(style -> style.withInsertion(insertion));
    }

    /**
     * Returns a new style with the font provided and all other attributes of this style.
     */
    public GeodeText withFont(@Nullable StyleSpriteSource font) {
        return modifyStyle(style -> style.withFont(font));
    }

    /**
     * Returns a new style with the formatting provided and all other attributes of this style.
     *
     * @param formatting the new formatting
     */
    public GeodeText withFormatting(Formatting formatting) {
        return modifyStyle(style -> style.withFormatting(formatting));
    }

    /**
     * Returns a new style with the formatting provided and some applicable attributes of this style.
     *
     * <p>When a color formatting is passed for {@code formatting}, the other
     * formattings, including bold, italic, strikethrough, underlined, and obfuscated, are all removed.
     *
     * @param formatting the new formatting
     */
    public GeodeText withExclusiveFormatting(Formatting formatting) {
        return modifyStyle(style -> style.withExclusiveFormatting(formatting));
    }

    /**
     * Returns a new style with the formattings provided and all other attributes of this style.
     *
     * @param formattings an array of new formattings
     */
    public GeodeText withFormatting(Formatting... formattings) {
        return modifyStyle(style -> style.withFormatting(formattings));
    }

    public static class Entry {
        public final MutableText text;
        public Style style;

        public Entry(MutableText text, Style style) {
            this.text = text;
            this.style = style;
        }

        public void styled(Function<Style, Style> modifier) {
            style = modifier.apply(style);
        }
    }
}