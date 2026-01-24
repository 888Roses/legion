package net.collectively.geode.mc.options;

import net.collectively.geode.Geode;
import net.collectively.geode.core.math;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class CustomOptionIndex {
    public enum OptionScreen {
        ACCESSIBILITY,
        CHAT,
        CONTROLS,
        MOUSE,
        SKIN,
        SOUND,
        VIDEO,

    }

    public record Option<T>(String id, SimpleOption<T> option, OptionScreen screen) {
    }

    public static final List<Option<?>> REGISTERED_OPTIONS = new ArrayList<>();
    public static final List<String> REMOVED_OPTIONS = new ArrayList<>();

    public static void removeOption(String id) {
        REMOVED_OPTIONS.add(id);
    }

    public static <T> Option<T> register(String id, SimpleOption<T> simpleOption, OptionScreen screen) {
        Option<T> option = new Option<>(id, simpleOption, screen);
        REGISTERED_OPTIONS.add(option);
        return option;
    }

    public static Option<Boolean> registerBoolean(String id, OptionScreen screen, boolean defaultValue) {
        return register(id, SimpleOption.ofBoolean("option." + Geode.HOOKED_MOD_ID + "." + id, defaultValue), screen);
    }

    public static Option<Double> registerPercentageSlider(
            String id,
            OptionScreen screen,
            double defaultValue
    ) {
        return register(
                id,
                new SimpleOption<>(
                        "option." + Geode.HOOKED_MOD_ID + "." + id,
                        SimpleOption.emptyTooltip(),
                        (prefix, value) -> Text.translatable(
                                "options.percent_value",
                                prefix,
                                (int) (value * (double) 100.0F)
                        ),
                        SimpleOption.DoubleSliderCallbacks.INSTANCE,
                        defaultValue,
                        value -> {
                        }
                ),
                screen
        );
    }

    public static Option<Double> registerSlider(
            String id,
            OptionScreen screen,
            double defaultValue,
            double min,
            double max
    ) {
        return register(
                id,
                new SimpleOption<>(
                        "option." + Geode.HOOKED_MOD_ID + "." + id,
                        SimpleOption.emptyTooltip(),
                        (prefix, value) -> Text.translatable(
                                "options.slider_value",
                                prefix,
                                math.round(math.lerp(value, min, max) * 10f) / 10f
                        ),
                        SimpleOption.DoubleSliderCallbacks.INSTANCE,
                        defaultValue,
                        value -> {
                        }
                ),
                screen
        );
    }

    public static Option<Double> registerSlider(
            String id,
            OptionScreen screen,
            SimpleOption.TooltipFactory<Double> tooltipFactory,
            SimpleOption.ValueTextGetter<Double> valueTextGetter,
            double defaultValue,
            Consumer<Double> callback
    ) {
        return register(
                id,
                new SimpleOption<>(
                        "option." + Geode.HOOKED_MOD_ID + "." + id,
                        tooltipFactory,
                        valueTextGetter,
                        SimpleOption.DoubleSliderCallbacks.INSTANCE,
                        defaultValue,
                        callback
                ),
                screen
        );
    }
}
