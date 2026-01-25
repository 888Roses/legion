package dev.rosenoire.legion.fundation.util;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class OrderedTextConverter implements CharacterVisitor {
    private MutableText text;

    @Override
    public boolean accept(int index, Style style, int codePoint) {
        if (text == null) {
            text = Text.literal("");
        }

        text = text.append(Text.literal(Character.toString(codePoint)).setStyle(style));
        return true;
    }

    public MutableText getText() {
        return this.text;
    }
}
