package net.collectively.geode.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.function.UnaryOperator;

public enum StandardEasingFunction implements EasingFunction {
    LINEAR("linear", x -> x),
    EASE_IN_SINE("ease_in_sine", x -> 1 - math.cos((x * math.pi) / 2)),
    EASE_OUT_SINE("ease_out_sine", x -> math.sin((x * math.pi) / 2)),
    EASE_IN_OUT_SINE("ease_in_out_sine", x -> -(math.cos(math.pi * x) - 1) / 2),
    EASE_IN_CUBIC("ease_in_cubic", x -> x * x * x),
    EASE_OUT_CUBIC("ease_out_cubic", x -> 1 - math.pow(1 - x, 3)),
    EASE_IN_OUT_CUBIC("ease_in_out_cubic", x -> x < 0.5 ? 4 * x * x * x : 1 - math.pow(-2 * x + 2, 3) / 2),
    EASE_IN_QUINT("ease_in_quint", x -> x * x * x * x * x),
    EASE_OUT_QUINT("ease_out_quint", x -> 1 - math.pow(1 - x, 5)),
    EASE_IN_OUT_QUINT("ease_in_out_quint", x -> x < 0.5 ? 16 * x * x * x * x * x : 1 - math.pow(-2 * x + 2, 5) / 2),
    EASE_IN_CIRC("ease_in_circ", x -> 1 - math.sqrt(1 - math.pow(x, 2))),
    EASE_OUT_CIRC("ease_out_circ", x -> math.sqrt(1 - math.pow(x - 1, 2))),
    EASE_IN_OUT_CIRC("ease_in_out_circ", x -> x < 0.5 ? (1 - math.sqrt(1 - math.pow(2 * x, 2))) / 2 : (math.sqrt(1 - math.pow(-2 * x + 2, 2)) + 1) / 2),
    EASE_IN_ELASTIC("ease_in_elastic", x -> x == 0 ? 0 : x == 1 ? 1 : -math.pow(2, 10 * x - 10) * math.sin((x * 10 - 10.75) * (2 * math.pi) / 3)),
    EASE_OUT_ELASTIC("ease_out_elastic", x -> x == 0 ? 0 : x == 1 ? 1 : math.pow(2, -10 * x) * math.sin((x * 10 - 0.75) * (2 * math.pi) / 3) + 1),
    EASE_IN_OUT_ELASTIC("ease_in_out_elastic", x -> x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? -(math.pow(2, 20 * x - 10) * math.sin((20 * x - 11.125) * (2 * math.pi) / 4.5)) / 2 : (math.pow(2, -20 * x + 10) * math.sin((20 * x - 11.125) * (2 * math.pi) / 4.5)) / 2 + 1),
    EASE_IN_QUAD("ease_in_quad", x -> x * x),
    EASE_OUT_QUAD("ease_out_quad", x -> 1 - (1 - x) * (1 - x)),
    EASE_IN_OUT_QUAD("ease_in_out_quad", x -> x < 0.5 ? 2 * x * x : 1 - math.pow(-2 * x + 2, 2) / 2),
    EASE_IN_QUART("ease_in_quart", x -> x * x * x * x),
    EASE_OUT_QUART("ease_out_quart", x -> 1 - math.pow(1 - x, 4)),
    EASE_IN_OUT_QUART("ease_in_out_quart", x -> x < 0.5 ? 8 * x * x * x * x : 1 - math.pow(-2 * x + 2, 4) / 2),
    EASE_IN_EXPO("ease_in_expo", x -> x == 0 ? 0 : math.pow(2, 10 * x - 10)),
    EASE_OUT_EXPO("ease_out_expo", x -> x == 1 ? 1 : 1 - math.pow(2, -10 * x)),
    EASE_IN_OUT_EXPO("ease_in_out_expo", x -> x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? math.pow(2, 20 * x - 10) / 2 : (2 - math.pow(2, -20 * x + 10)) / 2),
    EASE_IN_BACK("ease_in_back", x -> 1.70158 * x * x * x - 2.70158 * x * x),
    EASE_OUT_BACK("ease_out_back", x -> 1 + 2.70158 * math.pow(x - 1, 3) + 1.70158 * math.pow(x - 1, 2)),
    EASE_IN_OUT_BACK("ease_in_out_back", x -> x < 0.5 ? (math.pow(2 * x, 2) * ((2.5949095 + 1) * 2 * x - 2.5949095)) / 2 : (math.pow(2 * x - 2, 2) * ((2.5949095 + 1) * (x * 2 - 2) + 2.5949095) + 2) / 2),
    EASE_OUT_BOUNCE("ease_out_bounce", x -> {
        double n1 = 7.5625;
        double d1 = 2.75;

        if (x < 1 / d1) return n1 * x * x;
        else if (x < 2 / d1) return n1 * (x -= 1.5 / d1) * x + 0.75;
        else if (x < 2.5 / d1) return n1 * (x -= 2.25 / d1) * x + 0.9375;
        else return n1 * (x -= 2.625 / d1) * x + 0.984375;
    }),
    EASE_IN_BOUNCE("ease_in_bounce", x -> 1 - EASE_OUT_BOUNCE.get(1 - x)),
    EASE_IN_OUT_BOUNCE("ease_in_out_bounce", x -> x < 0.5 ? (1 - EASE_OUT_BOUNCE.get(1 - 2 * x)) / 2 : (1 + EASE_OUT_BOUNCE.get(2 * x - 1)) / 2),

    ;

    public static final Codec<StandardEasingFunction> CODEC = Codec.STRING.flatComapMap(
            name -> StandardEasingFunction.getFromName(name, LINEAR),
            easingFunction -> DataResult.success((easingFunction).getName())
    );

    private final UnaryOperator<Double> function;
    private final String name;

    StandardEasingFunction(String name, UnaryOperator<Double> function) {
        this.name = name;
        this.function = function;
    }

    @Override
    public double get(double x) {
        return function.apply(x);
    }

    public String getName() {
        return name;
    }

    public static StandardEasingFunction getFromName(String name, StandardEasingFunction fallback) {
        for (StandardEasingFunction func : StandardEasingFunction.values()) {
            if (func.getName().equals(name)) {
                return func;
            }
        }

        return fallback;
    }
}