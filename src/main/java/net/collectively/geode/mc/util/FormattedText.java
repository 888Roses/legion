package net.collectively.geode.mc.util;

import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class FormattedText {
    public record Entry(String text, Formatting[] formatting) {}
    private final List<Entry> entries = new ArrayList<>();

    public FormattedText() {}

    public FormattedText(Object text, Formatting... formats) {
        add(text, formats);
    }

    public FormattedText add(Object text, Formatting... formats) {
        Entry entry = new Entry(text.toString(), formats);
        entries.add(entry);
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Entry entry : entries) {
            stringBuilder.append("§r");

            for (Formatting formatting : entry.formatting) {
                stringBuilder.append("§");
                stringBuilder.append(formatting.getCode());
            }

            stringBuilder.append(entry.text);
        }

        return stringBuilder.toString();
    }
}
