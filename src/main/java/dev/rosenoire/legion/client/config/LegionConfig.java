package dev.rosenoire.legion.client.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class LegionConfig extends MidnightConfig {
    public static final String NORMAL = "normal";
    public static final String WARNING = "warning";

    // Client Catagory
    @Entry(category = NORMAL) public static boolean modEnabled = true;
    public static Comment splitter1;

    @Comment(category = NORMAL, name= "Configuration", centered = true)
    public static Comment comment1;
    @Entry(category = NORMAL) public static boolean showPotionInfo = true;
    @Entry(category = NORMAL) public static boolean showConsumableInfo = true;
    @Entry(category = NORMAL) public static boolean showTippedArrowInfo = true;
    @Entry(category = NORMAL) public static boolean showTotemInfo = true;
    @Entry(category = NORMAL) public static boolean showArmorInfo = true;


    // Illegal Catagory
    @Comment(category = WARNING, name= "(!) These options could be considered Cheating (!)", centered = true)
    public static Comment warning;

    @Entry(category = WARNING) public static boolean showSuspiciousStewInfo = false;

}
