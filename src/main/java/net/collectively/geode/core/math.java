package net.collectively.geode.core;

import net.collectively.geode.core.types.double2;
import net.collectively.geode.core.types.double3;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import org.joml.Quaternionf;

/**
 * Main geode math library.
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public interface math {
    // region min

    /**
     * Returns the smaller of two {@code double} values.  That is, the result is the value closer to negative infinity.
     * If the arguments have the same value, the result is that same value. If either value is {@code NaN}, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other is negative zero, the result
     * is negative zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The smaller of {@code a} and {@code b}.
     * @apiNote This method corresponds to the minimum operation defined in IEEE 754.
     */
    static double min(double a, double b) {
        return Math.min(a, b);
    }

    /**
     * Returns the smaller of two {@code float} values.  That is, the result is the value closer to negative infinity.
     * If the arguments have the same value, the result is that same value. If either value is {@code NaN}, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other is negative zero, the result
     * is negative zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The smaller of {@code a} and {@code b}.
     * @apiNote This method corresponds to the minimum operation defined in IEEE 754.
     */
    static float min(float a, float b) {
        return Math.min(a, b);
    }

    /**
     * Returns the smaller of two {@code int} values.  That is, the result is the value closer to negative infinity.
     * If the arguments have the same value, the result is that same value. If either value is {@code NaN}, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other is negative zero, the result
     * is negative zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The smaller of {@code a} and {@code b}.
     * @apiNote This method corresponds to the minimum operation defined in IEEE 754.
     */
    static int min(int a, int b) {
        return Math.min(a, b);
    }

    /**
     * Returns the smaller of two {@code long} values.  That is, the result is the value closer to negative infinity.
     * If the arguments have the same value, the result is that same value. If either value is {@code NaN}, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other is negative zero, the result
     * is negative zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The smaller of {@code a} and {@code b}.
     * @apiNote This method corresponds to the minimum operation defined in IEEE 754.
     */
    static long min(long a, long b) {
        return Math.min(a, b);
    }

    // endregion

    // region max

    /**
     * Returns the greater of two {@code double} values.  That is, the result is the argument closer to positive
     * infinity. If the arguments have the same value, the result is that same value. If either value is NaN, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other negative zero, the result
     * is positive zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The larger of {@code a} and {@code b}.
     * @apiNote This method corresponds to the maximum operation defined in IEEE 754.
     */
    static double max(double a, double b) {
        return Math.max(a, b);
    }

    /**
     * Returns the greater of two {@code float} values.  That is, the result is the argument closer to positive
     * infinity. If the arguments have the same value, the result is that same value. If either value is NaN, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other negative zero, the result
     * is positive zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The larger of {@code a} and {@code b}.
     * @apiNote This method corresponds to the maximum operation defined in IEEE 754.
     */
    static float max(float a, float b) {
        return Math.max(a, b);
    }

    /**
     * Returns the greater of two {@code int} values.  That is, the result is the argument closer to positive
     * infinity. If the arguments have the same value, the result is that same value. If either value is NaN, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other negative zero, the result
     * is positive zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The larger of {@code a} and {@code b}.
     * @apiNote This method corresponds to the maximum operation defined in IEEE 754.
     */
    static int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * Returns the greater of two {@code long} values.  That is, the result is the argument closer to positive
     * infinity. If the arguments have the same value, the result is that same value. If either value is NaN, then the
     * result is {@code NaN}.  Unlike the numerical comparison operators, this method considers negative zero to be
     * strictly smaller than positive zero. If one argument is positive zero and the other negative zero, the result
     * is positive zero.
     *
     * @param a An argument.
     * @param b Another argument.
     * @return The larger of {@code a} and {@code b}.
     * @apiNote This method corresponds to the maximum operation defined in IEEE 754.
     */
    static long max(long a, long b) {
        return Math.max(a, b);
    }

    // endregion

    // region clamp

    /**
     * Clamps the value to fit between min and max:
     * <ul>
     *     <li>If the value is less than {@code min}, then {@code min} is returned.</li>
     *     <li>If the value is greater than {@code max}, then {@code max} is returned.</li>
     *     <li>Otherwise, the original value is returned.</li>
     *     <li>If value is {@code NaN}, the result is also {@code NaN}.</li>
     * </ul>
     * Unlike the numerical comparison operators, this method considers negative zero to be strictly smaller than
     * positive zero (Example: {@code clamp(-0.0, 0.0, 1.0)} returns 0.0).
     *
     * @param value The value to clamp.
     * @param min   The minimal allowed value.
     * @param max   The maximal allowed value.
     * @return A clamped value that fits into {@code min..max} interval
     * @throws IllegalArgumentException if either of {@code min} and {@code max} arguments is {@code NaN}, or
     *                                  {@code min > max}, or {@code min} is +0.0, and {@code max} is -0.0.
     */
    static double clamp(double value, double min, double max) {
        return Math.clamp(value, min, max);
    }

    /**
     * Clamps the value to fit between min and max:
     * <ul>
     *     <li>If the value is less than {@code min}, then {@code min} is returned.</li>
     *     <li>If the value is greater than {@code max}, then {@code max} is returned.</li>
     *     <li>Otherwise, the original value is returned.</li>
     *     <li>If value is {@code NaN}, the result is also {@code NaN}.</li>
     * </ul>
     * Unlike the numerical comparison operators, this method considers negative zero to be strictly smaller than
     * positive zero (Example: {@code clamp(-0.0, 0.0, 1.0)} returns 0.0).
     *
     * @param value The value to clamp.
     * @param min   The minimal allowed value.
     * @param max   The maximal allowed value.
     * @return A clamped value that fits into {@code min..max} interval
     * @throws IllegalArgumentException if either of {@code min} and {@code max} arguments is {@code NaN}, or
     *                                  {@code min > max}, or {@code min} is +0.0, and {@code max} is -0.0.
     */
    static float clamp(float value, float min, float max) {
        return min(max, max(value, min));
    }

    /**
     * Clamps the value to fit between min and max:
     * <ul>
     *     <li>If the value is less than {@code min}, then {@code min} is returned.</li>
     *     <li>If the value is greater than {@code max}, then {@code max} is returned.</li>
     *     <li>Otherwise, the original value is returned.</li>
     *     <li>If value is {@code NaN}, the result is also {@code NaN}.</li>
     * </ul>
     * Unlike the numerical comparison operators, this method considers negative zero to be strictly smaller than
     * positive zero (Example: {@code clamp(-0.0, 0.0, 1.0)} returns 0.0).
     *
     * @param value The value to clamp.
     * @param min   The minimal allowed value.
     * @param max   The maximal allowed value.
     * @return A clamped value that fits into {@code min..max} interval
     * @throws IllegalArgumentException if either of {@code min} and {@code max} arguments is {@code NaN}, or
     *                                  {@code min > max}, or {@code min} is +0.0, and {@code max} is -0.0.
     */
    static int clamp(int value, int min, int max) {
        return min(max, max(value, min));
    }

    /**
     * Clamps the value to fit between min and max:
     * <ul>
     *     <li>If the value is less than {@code min}, then {@code min} is returned.</li>
     *     <li>If the value is greater than {@code max}, then {@code max} is returned.</li>
     *     <li>Otherwise, the original value is returned.</li>
     *     <li>If value is {@code NaN}, the result is also {@code NaN}.</li>
     * </ul>
     * Unlike the numerical comparison operators, this method considers negative zero to be strictly smaller than
     * positive zero (Example: {@code clamp(-0.0, 0.0, 1.0)} returns 0.0).
     *
     * @param value The value to clamp.
     * @param min   The minimal allowed value.
     * @param max   The maximal allowed value.
     * @return A clamped value that fits into {@code min..max} interval
     * @throws IllegalArgumentException if either of {@code min} and {@code max} arguments is {@code NaN}, or
     *                                  {@code min > max}, or {@code min} is +0.0, and {@code max} is -0.0.
     */
    static long clamp(long value, long min, long max) {
        return min(max, max(value, min));
    }

    // endregion

    // region clamp01

    /**
     * Clamps the value to fit between 0 and 1:
     * <ul>
     *     <li>If the value is less than {@code 0}, then {@code 0} is returned.</li>
     *     <li>If the value is greater than {@code 1}, then {@code 1} is returned.</li>
     *     <li>Otherwise, the original value is returned.</li>
     *     <li>If value is {@code NaN}, the result is also {@code NaN}.</li>
     * </ul>
     *
     * @param value The value to clamp.
     * @return A clamped value that fits into {@code 0..1} interval.
     * @see #clamp(double, double, double)
     */
    static double clamp01(double value) {
        return clamp(value, 0, 1);
    }

    /**
     * Clamps the value to fit between 0 and 1:
     * <ul>
     *     <li>If the value is less than {@code 0}, then {@code 0} is returned.</li>
     *     <li>If the value is greater than {@code 1}, then {@code 1} is returned.</li>
     *     <li>Otherwise, the original value is returned.</li>
     *     <li>If value is {@code NaN}, the result is also {@code NaN}.</li>
     * </ul>
     *
     * @param value The value to clamp.
     * @return A clamped value that fits into {@code 0..1} interval.
     * @see #clamp(float, float, float)
     */
    static float clamp01(float value) {
        return clamp(value, 0, 1);
    }

    // endregion

    // region ceil

    /**
     * Returns the smallest (closest to negative infinity) {@code double} value that is greater than or equal to the
     * argument and is equal to a mathematical integer.
     *
     * Special Cases:
     * <ul>
     *     <li>If the argument value is already equal to a mathematical integer, then the result is the same as the
     *     argument.</li>
     *     <li>If the argument is NaN or an infinity or positive zero or negative zero, then the result is the same as
     *     the argument.</li>
     *     <li>If the argument value is less than zero but greater than -1.0, then the result is negative zero.</li>
     * </ul>
     *
     * Note that the value of {@code Math.ceil(x)} is exactly the value of {@code -Math.floor(-x)}.
     *
     * @param value A value.
     * @return The smallest (closest to negative infinity) floating-point value that is greater than or equal to the
     * argument and is equal to a mathematical integer.
     * @apiNote This method corresponds to the roundToIntegralTowardPositive operation defined in IEEE 754.
     * @see Math#floor(double)
     */
    static long ceil(double value) {
        return (long) Math.ceil(value);
    }

    /**
     * Returns the smallest (closest to negative infinity) {@code float} value that is greater than or equal to the
     * argument and is equal to a mathematical integer.
     *
     * Special Cases:
     * <ul>
     *     <li>If the argument value is already equal to a mathematical integer, then the result is the same as the
     *     argument.</li>
     *     <li>If the argument is NaN or an infinity or positive zero or negative zero, then the result is the same as
     *     the argument.</li>
     *     <li>If the argument value is less than zero but greater than -1.0, then the result is negative zero.</li>
     * </ul>
     *
     * Note that the value of {@code Math.ceil(x)} is exactly the value of {@code -Math.floor(-x)}.
     *
     * @param value A value.
     * @return The smallest (closest to negative infinity) floating-point value that is greater than or equal to the
     * argument and is equal to a mathematical integer.
     * @apiNote This method corresponds to the roundToIntegralTowardPositive operation defined in IEEE 754.
     * @see Math#floor(double)
     */
    static int ceil(float value) {
        return (int) Math.ceil(value);
    }

    // endregion

    // region round

    /**
     * Returns the closest {@code long} to the argument, with ties rounding to positive infinity.
     *
     * Special Cases:
     * <ul>
     *     <li>If the argument is {@code NaN}, the result is {@code 0}.</li>
     *     <li>If the argument is negative infinity or any value less than or equal to the value of
     *     {@link Long#MIN_VALUE}, the result is equal to the value of {@link Long#MAX_VALUE}.</li>
     *     <li>If the argument is positive infinity or any value greater than or equal to the value of
     *     {@link Long#MIN_VALUE}, the result is equal to the value of {@link Long#MAX_VALUE}.</li>
     * </ul>
     *
     * @param value A floating-point value to be rounded to a {@code long}.
     * @return The value of the argument rounded to the nearest {@code long} value.
     * @see Long#MAX_VALUE
     * @see Long#MIN_VALUE
     */
    static long round(double value) {
        return Math.round(value);
    }

    /**
     * Returns the closest {@code int} to the argument, with ties rounding to positive infinity.
     *
     * Special Cases:
     * <ul>
     *     <li>If the argument is {@code NaN}, the result is 0.</li>
     *     <li>If the argument is negative infinity or any value less than or equal to the value of
     *     {@link Integer#MIN_VALUE}, the result is equal to the value of {@link Integer#MAX_VALUE}.</li>
     *     <li>If the argument is positive infinity or any value greater than or equal to the value of
     *     {@link Integer#MIN_VALUE}, the result is equal to the value of {@link Integer#MAX_VALUE}.</li>
     * </ul>
     *
     * @param value A floating-point value to be rounded to a {@code int}.
     * @return The value of the argument rounded to the nearest {@code int} value.
     * @see Long#MAX_VALUE
     * @see Long#MIN_VALUE
     */
    static int round(float value) {
        return Math.round(value);
    }
    // endregion

    // region abs

    /**
     * Returns the absolute value of a {@code double} value:
     * <ul>
     *     <li>If the argument is not negative, the argument is returned.</li>
     *     <li>If the argument is negative, the negation of the argument is returned.</li>
     * </ul>
     *
     * <br/>
     * Special Cases:
     * <ul>
     *     <li>If the argument is positive zero or negative zero, the result is positive zero.</li>
     *     <li>If the argument is infinite, the result is positive infinity.</li>
     *     <li>If the argument is {@code NaN}, the result is {@code NaN}.</li>
     * </ul>
     *
     * @param a The argument whose absolute value is to be determined
     * @return The absolute value of the argument.
     * @apiNote As implied by the above, one valid implementation of this method is given by the expression below which
     * computes a {@code double} with the same exponent and significand as the argument but with a guaranteed zero sign
     * bit indicating a positive value:<br>{@code Double.longBitsToDouble((Double.doubleToRawLongBits(a)<<1)>>>1)}
     */
    static double abs(double a) {
        return Math.abs(a);
    }

    /**
     * Returns the absolute value of a {@code float} value:
     * <ul>
     *     <li>If the argument is not negative, the argument is returned.</li>
     *     <li>If the argument is negative, the negation of the argument is returned.</li>
     * </ul>
     *
     * <br/>
     * Special Cases:
     * <ul>
     *     <li>If the argument is positive zero or negative zero, the result is positive zero.</li>
     *     <li>If the argument is infinite, the result is positive infinity.</li>
     *     <li>If the argument is {@code NaN}, the result is {@code NaN}.</li>
     * </ul>
     *
     * @param a The argument whose absolute value is to be determined
     * @return The absolute value of the argument.
     * @apiNote As implied by the above, one valid implementation of this method is given by the expression below which
     * computes a {@code double} with the same exponent and significand as the argument but with a guaranteed zero sign
     * bit indicating a positive value:<br>{@code Double.longBitsToDouble((Double.doubleToRawLongBits(a)<<1)>>>1)}
     */
    static float abs(float a) {
        return Math.abs(a);
    }

    /**
     * Returns the absolute value of a {@code int} value:
     * <ul>
     *     <li>If the argument is not negative, the argument is returned.</li>
     *     <li>If the argument is negative, the negation of the argument is returned.</li>
     * </ul>
     *
     * <br/>
     * Special Cases:
     * <ul>
     *     <li>If the argument is positive zero or negative zero, the result is positive zero.</li>
     *     <li>If the argument is infinite, the result is positive infinity.</li>
     *     <li>If the argument is {@code NaN}, the result is {@code NaN}.</li>
     * </ul>
     *
     * @param a The argument whose absolute value is to be determined
     * @return The absolute value of the argument.
     * @apiNote As implied by the above, one valid implementation of this method is given by the expression below which
     * computes a {@code double} with the same exponent and significand as the argument but with a guaranteed zero sign
     * bit indicating a positive value:<br>{@code Double.longBitsToDouble((Double.doubleToRawLongBits(a)<<1)>>>1)}
     */
    static int abs(int a) {
        return Math.abs(a);
    }

    /**
     * Returns the absolute value of a {@code long} value:
     * <ul>
     *     <li>If the argument is not negative, the argument is returned.</li>
     *     <li>If the argument is negative, the negation of the argument is returned.</li>
     * </ul>
     *
     * <br/>
     * Special Cases:
     * <ul>
     *     <li>If the argument is positive zero or negative zero, the result is positive zero.</li>
     *     <li>If the argument is infinite, the result is positive infinity.</li>
     *     <li>If the argument is {@code NaN}, the result is {@code NaN}.</li>
     * </ul>
     *
     * @param a The argument whose absolute value is to be determined
     * @return The absolute value of the argument.
     * @apiNote As implied by the above, one valid implementation of this method is given by the expression below which
     * computes a {@code double} with the same exponent and significand as the argument but with a guaranteed zero sign
     * bit indicating a positive value:<br>{@code Double.longBitsToDouble((Double.doubleToRawLongBits(a)<<1)>>>1)}
     */
    static long abs(long a) {
        return Math.abs(a);
    }

    // endregion

    // region quaternions

    /**
     * Creates a {@link Quaternionf} with the {@code radians} Euler rotation applied.
     *
     * @param radians The Euler rotation to apply measured in radians.
     * @return The rotation quaternion.
     * @see math#deg2rad(double)
     */
    static Quaternionf eulerRotation(double3 radians) {
        return new Quaternionf().rotateXYZ(
                (float) radians.x(),
                (float) radians.y(),
                (float) radians.z()
        );
    }

    // endregion

    // region invSqrt

    /**
     * The inverse square root algorithm as imagined by the Quake III developers.
     *
     * <h6>Explanation:</h6>
     *
     * The magic of the code, even if you can't follow it, stands out as the {@code i = 0x5f3759df - (i>>1);} line.
     * Simplified, Newton-Raphson is an approximation that starts off with a guess and refines it with iteration.
     * Taking advantage of the nature of 32-bit x86 processors, {@code i}, an {@code int}, is initially set to the value
     * of the floating point number you want to take the inverse square of, using an integer cast. {@code i} is then set
     * to {@code 0x5f3759df}, minus itself shifted one bit to the right. The right shift drops the least significant bit
     * of {@code i}, essentially halving it.<br/><br/>
     *
     * Using the integer cast of the seeded value, {@code i} is reused and the initial guess for Newton is
     * calculated using the magic seed value minus a free divide by 2 courtesy of the CPU.<br/><br/>
     *
     * <h6>Sources:</h6>
     * Implementation by <a href="https://stackoverflow.com/a/11513345">this reddit user</a>.<br/>
     * Explanation by: <a href="https://www.beyond3d.com/content/articles/8/">this beyond3d article</a> written by <u>Rys for Software</u>.<br/>
     *
     * @param a The value to square.
     * @return The fast inverse square root result.
     */
    static float invSqrt(float a) {
        float xhalf = 0.5f * a;
        int i = Float.floatToIntBits(a);
        i = 0x5f3759df - (i >> 1);
        a = Float.intBitsToFloat(i);
        a *= (1.5f - xhalf * a * a);
        return a;
    }

    /**
     * The inverse square root algorithm as imagined by the Quake III developers.
     *
     * <h6>Explanation:</h6>
     *
     * The magic of the code, even if you can't follow it, stands out as the {@code i = 0x5f3759df - (i>>1);} line.
     * Simplified, Newton-Raphson is an approximation that starts off with a guess and refines it with iteration.
     * Taking advantage of the nature of 32-bit x86 processors, {@code i}, an {@code int}, is initially set to the value
     * of the floating point number you want to take the inverse square of, using an integer cast. {@code i} is then set
     * to {@code 0x5f3759df}, minus itself shifted one bit to the right. The right shift drops the least significant bit
     * of {@code i}, essentially halving it.<br/><br/>
     *
     * Using the integer cast of the seeded value, {@code i} is reused and the initial guess for Newton is
     * calculated using the magic seed value minus a free divide by 2 courtesy of the CPU.<br/><br/>
     *
     * <h6>Sources:</h6>
     * Implementation by <a href="https://stackoverflow.com/a/11513345">this reddit user</a>.<br/>
     * Explanation by: <a href="https://www.beyond3d.com/content/articles/8/">this beyond3d article</a> written by <u>Rys for Software</u>.<br/>
     *
     * @param a The value to square.
     * @return The fast inverse square root result.
     */
    static double invSqrt(double a) {
        double xhalf = 0.5d * a;
        long i = Double.doubleToLongBits(a);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        a = Double.longBitsToDouble(i);
        a *= (1.5d - xhalf * a * a);
        return a;
    }

    // endregion

    // region sqrt

    /**
     * Returns the correctly rounded positive square root of a {@code double} value.
     * Special Cases:
     * <ul>
     *     <li>If the argument is NaN or less than zero, then the result is {@code NaN}.</li>
     *     <li>If the argument is positive infinity, then the result is positive infinity.</li>
     *     <li>If the argument is positive zero or negative zero, then the result is the same as the argument.</li>
     * </ul>
     *
     * Otherwise, the result is the {@code double} value closest to the true mathematical square root of the argument
     * value.
     *
     * @param a A value.
     * @return The positive square root of {@code a}. If the argument is {@code NaN} or less than zero, the result is
     * {@code NaN}.
     * @apiNote This method corresponds to the squareRoot operation defined in IEEE 754.
     */
    static double sqrt(double a) {
        return Math.sqrt(a);
    }

    // endregion

    /**
     * The {@code double} value that is closer than any other to
     * <i>pi</i> (&pi;), the ratio of the circumference of a circle to
     * its diameter.
     */
    double pi = Math.PI;


    /// Returns the value of the first argument raised to the power of the
    /// second argument. Special cases:
    ///   - If the second argument is positive or negative zero, then the
    ///     result is 1.0.
    ///   - If the second argument is 1.0, then the result is the same as the
    ///     first argument.
    ///   - If the second argument is NaN, then the result is NaN.
    ///   - If the first argument is NaN and the second argument is nonzero,
    ///     then the result is NaN.
    ///   - If the absolute value of the first argument is greater than 1
    ///     and the second argument is positive infinity, or
    ///   - the absolute value of the first argument is less than 1 and
    ///     the second argument is negative infinity,
    ///
    /// then the result is positive infinity.
    ///   - If the absolute value of the first argument is greater than 1 and
    ///     the second argument is negative infinity, or
    ///   - the absolute value of the
    ///     first argument is less than 1 and the second argument is positive
    ///     infinity,
    ///
    /// then the result is positive zero.
    ///   - If the absolute value of the first argument equals 1 and the
    ///     second argument is infinite, then the result is NaN.
    ///   - If the first argument is positive zero and the second argument
    ///     is greater than zero, or
    ///   - the first argument is positive infinity and the second
    ///     argument is less than zero,
    ///
    /// then the result is positive zero.
    ///   - If the first argument is positive zero and the second argument
    ///     is less than zero, or
    ///   - the first argument is positive infinity and the second
    ///     argument is greater than zero,
    ///
    /// then the result is positive infinity.
    ///   - If the first argument is negative zero and the second argument
    ///     is greater than zero but not a finite odd integer, or
    ///   - the first argument is negative infinity and the second
    ///     argument is less than zero but not a finite odd integer,
    ///
    /// then the result is positive zero.
    ///   - If the first argument is negative zero and the second argument
    ///     is a positive finite odd integer, or
    ///   - the first argument is negative infinity and the second
    ///     argument is a negative finite odd integer,
    ///
    /// then the result is negative zero.
    ///   - If the first argument is negative zero and the second argument
    ///     is less than zero but not a finite odd integer, or
    ///   - the first argument is negative infinity and the second
    ///     argument is greater than zero but not a finite odd integer,
    ///
    /// then the result is positive infinity.
    ///   - If the first argument is negative zero and the second argument
    ///     is a negative finite odd integer, or
    ///   - the first argument is negative infinity and the second
    ///     argument is a positive finite odd integer,
    ///
    /// then the result is negative infinity.
    ///   - If the first argument is finite and less than zero
    ///   - if the second argument is a finite even integer, the
    ///     result is equal to the result of raising the absolute value of
    ///     the first argument to the power of the second argument
    ///   - if the second argument is a finite odd integer, the result
    ///     is equal to the negative of the result of raising the absolute
    ///     value of the first argument to the power of the second
    ///     argument
    ///   - if the second argument is finite and not an integer, then
    ///     the result is NaN.
    ///   - If both arguments are integers, then the result is exactly equal
    ///     to the mathematical result of raising the first argument to the power
    ///     of the second argument if that result can in fact be represented
    ///     exactly as a `double` value.
    ///
    /// (In the foregoing descriptions, a floating-point value is
    /// considered to be an integer if and only if it is finite and a
    /// fixed point of the method [ceil][#ceil] or,
    /// equivalently, a fixed point of the method floor.
    /// A value is a fixed point of a one-argument
    /// method if and only if the result of applying the method to the
    /// value is equal to the value.)
    ///
    /// The computed result must be within 1 ulp of the exact result.
    /// Results must be semi-monotonic.
    ///
    /// @apiNote
    /// The special cases definitions of this method differ from the
    /// special case definitions of the IEEE 754 recommended
    /// `pow` operation for &plusmn;`1.0` raised to an infinite
    /// power. This method treats such cases as indeterminate and
    /// specifies a NaN is returned. The IEEE 754 specification treats
    /// the infinite power as a large integer (large-magnitude
    /// floating-point numbers are numerically integers, specifically
    /// even integers) and therefore specifies `1.0` be returned.
    ///
    /// @param   a   the base.
    /// @param   b   the exponent.
    /// @return  the value `a`<sup>`b`</sup>.
    static double pow(double a, double b) {
        return StrictMath.pow(a, b); // default impl. delegates to StrictMath
    }

    // region rad & deg

    /**
     * Converts an angle measured in radians to an approximately equivalent angle measured in degrees. The conversion
     * from radians to degrees is generally inexact; users should <u>not</u> expect {@code cos(toRadians(90.0))} to
     * exactly equal {@code 0.0}.
     *
     * @param rad An angle, in radians
     * @return The measurement of the angle {@code rad} in degrees.
     */
    static double rad2deg(double rad) {
        return Math.toDegrees(rad);
    }

    /**
     * Converts an angle measured in degrees to an approximately equivalent angle measured in radians. The conversion
     * from degrees to radians is generally inexact.
     *
     * @param deg An angle, in degrees
     * @return The measurement of the angle {@code deg}
     * in radians.
     */
    static double deg2rad(double deg) {
        return Math.toRadians(deg);
    }

    /**
     * Converts an angle measured in radians to an approximately equivalent angle measured in degrees. The conversion
     * from radians to degrees is generally inexact; users should <u>not</u> expect {@code cos(toRadians(90.0))} to
     * exactly equal {@code 0.0}.
     *
     * @param rad An angle, in radians
     * @return The measurement of the angle {@code rad} in degrees.
     */
    static float rad2deg(float rad) {
        return (float) rad2deg((double) rad);
    }

    /**
     * Converts an angle measured in degrees to an approximately equivalent angle measured in radians. The conversion
     * from degrees to radians is generally inexact.
     *
     * @param deg An angle, in degrees
     * @return The measurement of the angle {@code deg}
     * in radians.
     */
    static float deg2rad(float deg) {
        return (float) deg2rad((double) deg);
    }

    // endregion

    /**
     * @return an approximation of {@code Math.atan2(y, x)}
     * @implNote This implementation transforms the arguments such that they lie in the first quadrant.
     * <ul>
     *     <li>If {@code y > x}, then {@code x} and {@code y} are swapped to minimize the error of the initial approximation.</li>
     *     <li>{@code x} and {@code y} are normalized, and an initial approximation of the result and the sine of the
     *     deviation from the true value are obtained using the {@link MathHelper#ARCSINE_TABLE} and
     *     {@link MathHelper#COSINE_OF_ARCSINE_TABLE} lookup tables. The error itself is approximated using the
     *     third-order Maclaurin series polynomial for arcsin.</li>
     *     <li> Finally, the implementation undoes any transformations that were performed initially.</li>
     * </ul>
     */
    static double atan2(double y, double x) {
        return MathHelper.atan2(y, x);
    }

    /**
     * Turns a {@link double3} vector into a {@link double2} radians rotation.
     *
     * @param a The direction vector of that rotation.
     * @return The rotation measured in radians.
     * @apiNote Warning, <u>this method is expensive</u>: It calls {@link #atan2(double, double)} multiple times, as
     * well as making use of {@link #sqrt(double)}.
     */
    static double2 vec2angle(double3 a) {
        return new double2(atan2(a.y(), sqrt(a.squaredHorMag())), atan2(a.x(), a.z()));
    }

    static double lerp(double delta, double a, double b) {
        return a + delta * (b - a);
    }

    static float lerp(float delta, float a, float b) {
        return a + delta * (b - a);
    }

    static double2 lerp(double delta, double2 a, double2 b) {
        return new double2(lerp(delta, a.x(), b.x()), lerp(delta, a.y(), b.y()));
    }

    static double3 lerp(double delta, double3 a, double3 b) {
        return new double3(lerp(delta, a.x(), b.x()), lerp(delta, a.y(), b.y()), lerp(delta, a.z(), b.z()));
    }

    static double3 lerp(double delta, Position a, Position b) {
        return new double3(lerp(delta, a.getX(), b.getX()), lerp(delta, a.getY(), b.getY()), lerp(delta, a.getZ(), b.getZ()));
    }

    /**
     * Returns the trigonometric sine of an angle.  Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the
     * result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param a an angle, in radians.
     * @return the sine of the argument.
     */
    static double sin(double a) {
        return StrictMath.sin(a); // default impl. delegates to StrictMath
    }

    /**
     * Returns the trigonometric cosine of an angle. Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the
     * result is NaN.
     * <li>If the argument is zero, then the result is {@code 1.0}.
     * </ul>
     *
     * The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param a an angle, in radians.
     * @return the cosine of the argument.
     */
    static double cos(double a) {
        return StrictMath.cos(a); // default impl. delegates to StrictMath
    }

    /**
     * Returns the trigonometric tangent of an angle.  Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the result
     * is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param a an angle, in radians.
     * @return the tangent of the argument.
     */
    static double tan(double a) {
        return StrictMath.tan(a); // default impl. delegates to StrictMath
    }

    /**
     * Returns the arc sine of a value; the returned angle is in the
     * range -<i>pi</i>/2 through <i>pi</i>/2.  Special cases:
     * <ul><li>If the argument is NaN or its absolute value is greater
     * than 1, then the result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param a the value whose arc sine is to be returned.
     * @return the arc sine of the argument.
     */
    static double asin(double a) {
        return StrictMath.asin(a); // default impl. delegates to StrictMath
    }

    /**
     * Returns the arc cosine of a value; the returned angle is in the
     * range 0.0 through <i>pi</i>.  Special case:
     * <ul><li>If the argument is NaN or its absolute value is greater
     * than 1, then the result is NaN.
     * <li>If the argument is {@code 1.0}, the result is positive zero.
     * </ul>
     *
     * The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param a the value whose arc cosine is to be returned.
     * @return the arc cosine of the argument.
     */
    static double acos(double a) {
        return StrictMath.acos(a); // default impl. delegates to StrictMath
    }

    /**
     * Returns the arc tangent of a value; the returned angle is in the
     * range -<i>pi</i>/2 through <i>pi</i>/2.  Special cases:
     * <ul><li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     * <li>If the argument is {@linkplain Double#isInfinite infinite},
     * then the result is the closest value to <i>pi</i>/2 with the
     * same sign as the input.
     * </ul>
     *
     * The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     *
     * @param a the value whose arc tangent is to be returned.
     * @return the arc tangent of the argument.
     */
    static double atan(double a) {
        return StrictMath.atan(a); // default impl. delegates to StrictMath
    }

    /**
     * Rotates a vector by an angle.
     *
     * @param v   The vector to rotate.
     * @param rot The rotation vector where X is the pitch, Y the yaw and Z the roll.
     * @return The rotated vector.
     * @implNote Please note that radians are expected for the rotation.
     */
    static double3 rotateVector(double3 v, double3 rot) {
        return new double3(math.eulerRotation(rot).transformInverse(v.toVec3f()));
    }
}