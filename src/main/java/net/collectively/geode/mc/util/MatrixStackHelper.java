package net.collectively.geode.mc.util;

import net.collectively.geode.core.math;
import net.collectively.geode.core.types.*;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Collection of utilities around {@link MatrixStack}.
 */
@SuppressWarnings("unused")
public interface MatrixStackHelper {
    /**
     * Translates the given matrix stack by the given vector.
     *
     * @param matrixStack The matrix to translate.
     * @param v           The translation vector.
     */
    static void translate(MatrixStack matrixStack, double3 v) {
        matrixStack.translate(v.toVec3d());
    }

    /**
     * Scales the given matrix stack by the given vector.
     *
     * @param matrixStack The stack to scale.
     * @param s           The scale multiplier vector.
     */
    static void scale(MatrixStack matrixStack, double3 s) {
        scale(matrixStack, s, double3.zero);
    }

    /**
     * Scales the given matrix stack by the given vector.
     *
     * @param matrixStack The stack to scale.
     * @param s           The scale multiplier vector.
     * @param point The origin relative to which we want to scale.
     */
    static void scale(MatrixStack matrixStack, double3 s, double3 point) {
        if (!point.isZero()) {
            translate(matrixStack, point);
        }

        matrixStack.scale((float) s.x(), (float) s.y(), (float) s.z());

        if (!point.isZero()) {
            translate(matrixStack, point.mul(-1));
        }
    }

    /**
     * Applies an Euler rotation to the given matrix stack by the given vector around an origin point. This uses radians
     * as the rotation unit.
     *
     * @param matrixStack The stack to be rotated.
     * @param radians     The Euler rotation to apply to this stack.
     * @param point       The origin point around which we rotate the stack.
     * @apiNote If the origin point {@link double3#isZero()}, the matrix stack translation operation will be skipped
     * entirely.
     */
    static void rotate(MatrixStack matrixStack, double3 radians, double3 point) {
        if (!point.isZero()) {
            translate(matrixStack, point);
        }

        matrixStack.multiply(math.eulerRotation(radians));

        if (!point.isZero()) {
            translate(matrixStack, point.mul(-1));
        }
    }

    /**
     * Applies an Euler rotation to the given matrix stack by the given vector around the zero origin point. This uses
     * radians as the rotation unit.
     *
     * @param matrixStack The stack to be rotated.
     * @param radians     The Euler rotation to apply to this stack.
     */
    static void rotate(MatrixStack matrixStack, double3 radians) {
        rotate(matrixStack, radians, double3.zero);
    }
}