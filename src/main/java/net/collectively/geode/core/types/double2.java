package net.collectively.geode.core.types;

import com.mojang.serialization.Codec;
import net.collectively.geode.core.math;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec2f;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.util.List;

@SuppressWarnings({
        "unused",
        "SuspiciousNameCombination"
})
public record double2(double x, double y) implements Comparable<double2> {
    // region Serialization
    public static final Codec<double2> CODEC = Codec.DOUBLE.listOf().comapFlatMap(
            c -> Util.decodeFixedLengthList(c, 2)
                    .map(coords -> new double2(coords.getFirst(), coords.get(1))),
            v -> List.of(v.x, v.y)
    );
    // endregion

    // region Constructors
    public double2(double x) {this(x,x);}
    public double2(Vector2d xy) {this(xy.x,xy.y);}
    public double2(Vector2f xy) {this(xy.x,xy.y);}
    public double2(Vec2f xy) {this(xy.x,xy.y);}
    // endregion

    // region Conversions
    public Vector2d toVector2d() {return new Vector2d(x,y);}
    public Vector2f toVector2f() {return new Vector2f((float)x,(float)y);}
    public Vec2f toVec2f() {return new Vec2f((float)x,(float)y);}
    // endregion

    // region Comparable

    @Override
    public int compareTo(double2 other) {
        return (int) Math.round(this.y==other.y?this.x-other.x:this.y-other.y);
    }

    // endregion

    // region Math

    // region vector math

    public double2 normalize() {
        var squaredMagnitude =x*x+y*y;
        var inv = math.invSqrt(squaredMagnitude);
        return new double2(x*inv,y*inv);
    }

    // endregion

    // region add
    public double2 add(double2 xy) {return new double2(x+xy.x,y+xy.y);}
    public double2 add(double x,double y) {return add(new double2(x,y));}
    public double2 add(double x) {return add(x,x);}
    public double2 addX(double x) {return add(x,0);}
    public double2 addY(double y) {return add(0,y);}
    // endregion

    // region add
    public double2 sub(double2 xy) {return new double2(x-xy.x,y-xy.y);}
    public double2 sub(double x,double y) {return sub(new double2(x,y));}
    public double2 sub(double x) {return sub(x,x);}
    public double2 subX(double x) {return sub(x,0);}
    public double2 subY(double y) {return sub(0,y);}
    // endregion

    // region add
    public double2 mul(double2 xy) {return new double2(x*xy.x,y*xy.y);}
    public double2 mul(double x,double y) {return mul(new double2(x,y));}
    public double2 mul(double x) {return mul(x,x);}
    public double2 mulX(double x) {return mul(x,0);}
    public double2 mulY(double y) {return mul(0,y);}
    // endregion

    // region div
    public double2 div(double2 xy) {return new double2(x/xy.x,y/xy.y);}
    public double2 div(double x,double y) {return div(new double2(x,y));}
    public double2 div(double x) {return div(x,x);}
    public double2 divX(double x) {return div(x,0);}
    public double2 divY(double y) {return div(0,y);}
    // endregion

    public double2 lerp(double delta, double2 to) {return math.lerp(delta, this, to);}

    // endregion
}