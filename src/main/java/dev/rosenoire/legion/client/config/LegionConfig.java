package dev.rosenoire.legion.client.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class LegionConfig extends MidnightConfig {
    public static final String TEXT = "text";

    @Entry(category = TEXT) public static boolean showSuspiciousStewInfo = false;

}
