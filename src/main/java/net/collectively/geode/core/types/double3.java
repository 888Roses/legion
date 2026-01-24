package net.collectively.geode.core.types;

import com.mojang.serialization.Codec;
import net.collectively.geode.core.math;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.UnaryOperator;

@SuppressWarnings({
        "unused",
        "SuspiciousNameCombination"
})
public record double3(double x, double y, double z) implements Position, Comparable<Position> {
    // region Serialization
    public static final Codec<double3> CODEC = Codec.DOUBLE.listOf().comapFlatMap(
        c -> Util.decodeFixedLengthList(c, 3).map(coords -> new double3(coords.getFirst(), coords.get(1), coords.get(2))),
        v -> List.of(v.x, v.y, v.z)
    );
    // endregion

    // region Constants
    public static double3 zero = new double3(0);
    public static double3 one = new double3(1);
    public static double3 half = new double3(0.5);
    public static double3 right = new double3(1,0,0);
    public static double3 left = new double3(-1,0,0);
    public static double3 up = new double3(0,1,0);
    public static double3 down = new double3(0,-1,0);
    public static double3 fwd = new double3(0,0,1);
    public static double3 bwd = new double3(0,0,-1);
    // endregion

    // region Position
    @Override public double getX() { return this.x; }
    @Override public double getY() { return this.y; }
    @Override public double getZ() { return this.z; }
    // endregion

    // region Constructors
    public double3(double x, double y) {this(x,y,0);}
    public double3(double x) {this(x,x,x);}
    public double3(Position xyz) {this(xyz.getX(),xyz.getY(),xyz.getZ());}
    public double3(BlockPos pos) {this(pos.getX(),pos.getY(),pos.getZ());}
    public double3(Vector3f xyz) {this(xyz.x(),xyz.y(),xyz.z());}
    // endregion

    // region Conversions

    public Vec3d toVec3d() {return new Vec3d(x,y,z);}
    public BlockPos toBlockPos() {return BlockPos.ofFloored(this);}
    public Vector3f toVec3f() {return new Vector3f((float)x,(float)y,(float)z);}
    public double3 modify(UnaryOperator<Double> modifier) {
        return new double3(modifier.apply(x), modifier.apply(y), modifier.apply(z));
    }

    // endregion

    // region Comparable

    @Override
    public int compareTo(Position other) {
        if (this.getY() == other.getY()) return (int) Math.round(this.getZ() == other.getZ() ? this.getX() - other.getX() : this.getZ() - other.getZ());
        return (int) Math.round(this.getY() - other.getY());
    }

    public boolean isZero() {return x==0&&y==0&&z==0;}

    // endregion

    // region Math

    // region vector math

    public double3 normalize() {
        return this.mul(math.invSqrt(squaredMag()));
    }

    public double squaredMag() {return x*x+y*y+z*z;}
    public double squaredHorMag() {return x*x+z*z;}
    public double3 cross(Position v) { return new double3(y * v.getZ() - z * v.getY(), z * v.getX() - x * v.getZ(), x * v.getY() - y * v.getX()); }
    public double dot(Position vec) {
        return this.x * vec.getX() + this.y * vec.getY() + this.z * vec.getZ();
    }

    // endregion

    // region add

    public double3 add(Position xyz) {return new double3(x+xyz.getX(),y+xyz.getY(),z+xyz.getZ());}
    public double3 add(BlockPos xyz) {return add(xyz.toCenterPos());}
    public double3 add(double x,double y,double z) {return add(new double3(x,y,z));}
    public double3 add(double x) {return add(x,x,x);}
    public double3 addX(double x) {return add(x,0,0);}
    public double3 addY(double y) {return add(0,y,0);}
    public double3 addZ(double z) {return add(0,0,z);}

    // endregion

    // region sub

    public double3 sub(Position xyz) {return new double3(x-xyz.getX(),y-xyz.getY(),z-xyz.getZ());}
    public double3 sub(BlockPos xyz) {return sub(xyz.toCenterPos());}
    public double3 sub(double x,double y,double z) {return sub(new double3(x,y,z));}
    public double3 sub(double x) {return sub(x,x,x);}
    public double3 subX(double x) {return sub(x,0,0);}
    public double3 subY(double y) {return sub(0,y,0);}
    public double3 subZ(double z) {return sub(0,0,z);}

    // endregion

    // region mul

    public double3 mul(Position xyz) {return new double3(x*xyz.getX(),y*xyz.getY(),z*xyz.getZ());}
    public double3 mul(BlockPos xyz) {return mul(xyz.toCenterPos());}
    public double3 mul(double x,double y,double z) {return mul(new double3(x,y,z));}
    public double3 mul(double x) {return mul(x,x,x);}
    public double3 mulX(double x) {return mul(x,0,0);}
    public double3 mulY(double y) {return mul(0,y,0);}
    public double3 mulZ(double z) {return mul(0,0,z);}

    // endregion

    // region div

    public double3 div(Position xyz) {return new double3(x/xyz.getX(),y/xyz.getY(),z/xyz.getZ());}
    public double3 div(BlockPos xyz) {return div(xyz.toCenterPos());}
    public double3 div(double x,double y,double z) {return div(new double3(x,y,z));}
    public double3 div(double x) {return div(x,x,x);}
    public double3 divX(double x) {return div(x,0,0);}
    public double3 divY(double y) {return div(0,y,0);}
    public double3 divZ(double z) {return div(0,0,z);}

    // endregion

    public double3 lerp(double delta, Position to) {return math.lerp(delta, this, to);}

    // endregion

    // region Channels

    public double3 withX(double x) {return new double3(x, y, z);}
    public double3 withY(double y) {return new double3(x, y, z);}
    public double3 withZ(double z) {return new double3(z, y, this.z);}

    public double3 xyz() {
        return new double3(x, y, z);
    }
    public double3 zyx() {
        return new double3(z, y, x);
    }
    public double3 zxy() {
        return new double3(z, x, y);
    }
    public double3 yxz() {
        return new double3(y, x, z);
    }
    public double3 yzx() {
        return new double3(y, z, x);
    }
    public double3 xzy() {
        return new double3(x, z, y);
    }

    public double2 xx() {
        return new double2(x, x);
    }
    public double2 xy() {
        return new double2(x, y);
    }
    public double2 xz() {
        return new double2(x, z);
    }
    public double2 yx() {
        return new double2(y, x);
    }
    public double2 yy() {
        return new double2(y, y);
    }
    public double2 yz() {
        return new double2(y, z);
    }
    public double2 zx() {
        return new double2(z, x);
    }
    public double2 zy() {
        return new double2(z, y);
    }
    public double2 zz() {
        return new double2(z, z);
    }

    // endregion

    public String toPrettyString() {
        return "[%s, %s, %s]".formatted(x,y,z);
    }
}
