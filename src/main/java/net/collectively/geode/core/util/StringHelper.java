package net.collectively.geode.core.util;

import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public interface StringHelper {
    /**
     * Removes the leading and tailing empty lines from the given {@code initialLines}.
     * @param initialLines A {@link List} of lines to strip.
     * @return A {@link List} of stripped {@code String} lines.
     */
    static List<String> stripLines(List<String> initialLines) {
        int nonEmptyLineStartIndex = 0;
        int nonEmptyLineEndIndex = initialLines.size();

        for (String initialLine : initialLines) {
            String line = initialLine.strip();

            if (line.isEmpty()) {
                nonEmptyLineStartIndex++;
                continue;
            }

            if (line.startsWith("#")) {
                if (line.substring(1).stripIndent().startsWith("enable mcpp")) {
                    nonEmptyLineStartIndex++;
                    continue;
                }
            }

            break;
        }

        for (int i = initialLines.size() - 1; i >= 0; i--) {
            String line = initialLines.get(i).strip();

            if (line.isEmpty()) {
                nonEmptyLineEndIndex--;
                continue;
            }

            break;
        }

        return initialLines.subList(nonEmptyLineStartIndex, nonEmptyLineEndIndex);
    }

    /**
     * Counts the amount of {@code character} at the start of the given {@code String}.
     * @param string The text to check for.
     * @param character The character to check for.
     * @return The amount of {@code character} at the start of the {@code string}.
     */
    static int countLeading(String string, Predicate<Character> character) {
        for (int i = 0; i < string.length(); i++) if (character.test(string.charAt(i))) return i;
        return 0;
    }

    /**
     * Counts the amount of {@code character} at the end of the given {@code String}.
     * @param string The text to check for.
     * @param character The character to check for.
     * @return The amount of {@code character} at the end of the {@code string}.
     */
    static int countTailing(String string, Predicate<Character> character) {
        for (int i = string.length() - 1; i >= 0; i--) if (character.test(string.charAt(i))) return i;
        return 0;
    }
}