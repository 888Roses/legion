package net.collectively.geode.debug;

import net.collectively.geode.core.types.*;
import net.minecraft.client.render.DrawStyle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.debug.gizmo.GizmoDrawing;
import net.minecraft.world.debug.gizmo.TextGizmo;
import net.minecraft.world.debug.gizmo.VisibilityConfigurable;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface Draw {
    static VisibilityConfigurable box(double3 position, double3 size, int colour, boolean cornerColouredStroke) {
        return GizmoDrawing.box(
                Box.of(position.toVec3d(), size.x(), size.y(), size.z()),
                DrawStyle.stroked(colour),
                cornerColouredStroke
        );
    }

    static VisibilityConfigurable box(double3 position, double3 size, int colour) {
        return box(position, size, colour, false);
    }

    static VisibilityConfigurable box(double3 position, double3 size, boolean cornerColouredStroke) {
        return box(position, size, 0xffFFFFFF, cornerColouredStroke);
    }

    static VisibilityConfigurable box(double3 position, double3 size) {
        return box(position, size, 0xffFFFFFF);
    }

    static VisibilityConfigurable box(BlockPos position, double3 size, int colour, boolean cornerColouredStroke) {
        return box(new double3(position), size, colour, cornerColouredStroke);
    }

    static VisibilityConfigurable box(BlockPos position, double3 size, int colour) {
        return box(new double3(position), size, colour);
    }

    static VisibilityConfigurable box(BlockPos position, double3 size, boolean cornerColouredStroke) {
        return box(new double3(position), size, cornerColouredStroke);
    }

    static VisibilityConfigurable box(BlockPos position, double3 size) {
        return box(new double3(position), size);
    }


    static VisibilityConfigurable line(double3 a, double3 b, int colour, float width) {
        return GizmoDrawing.line(a.toVec3d(), b.toVec3d(), colour, width);
    }

    static VisibilityConfigurable line(double3 a, double3 b, int colour) {
        return line(a, b, colour, 3);
    }

    static VisibilityConfigurable line(double3 a, double3 b, float width) {
        return GizmoDrawing.line(a.toVec3d(), b.toVec3d(), 0xffFFFFFF, width);
    }

    static VisibilityConfigurable line(double3 a, double3 b) {
        return GizmoDrawing.line(a.toVec3d(), b.toVec3d(), 0xffFFFFFF);
    }

    static VisibilityConfigurable line(BlockPos a, BlockPos b, int colour, float width) {
        return line(new double3(a), new double3(b), colour, width);
    }

    static VisibilityConfigurable line(BlockPos a, BlockPos b, int colour) {
        return line(new double3(a), new double3(b), colour);
    }

    static VisibilityConfigurable line(BlockPos a, BlockPos b, float width) {
        return line(new double3(a), new double3(b), width);
    }

    static VisibilityConfigurable line(BlockPos a, BlockPos b) {
        return line(new double3(a), new double3(b));
    }


    static VisibilityConfigurable arrow(double3 a, double3 b, int colour, float width) {
        return GizmoDrawing.arrow(a.toVec3d(), b.toVec3d(), colour, width);
    }

    static VisibilityConfigurable arrow(double3 a, double3 b, int colour) {
        return line(a, b, colour, 3);
    }

    static VisibilityConfigurable arrow(double3 a, double3 b, float width) {
        return GizmoDrawing.arrow(a.toVec3d(), b.toVec3d(), 0xffFFFFFF, width);
    }

    static VisibilityConfigurable arrow(double3 a, double3 b) {
        return GizmoDrawing.arrow(a.toVec3d(), b.toVec3d(), 0xffFFFFFF);
    }

    static VisibilityConfigurable arrow(BlockPos a, BlockPos b, int colour, float width) {
        return arrow(new double3(a), new double3(b), colour, width);
    }

    static VisibilityConfigurable arrow(BlockPos a, BlockPos b, int colour) {
        return arrow(new double3(a), new double3(b), colour);
    }

    static VisibilityConfigurable arrow(BlockPos a, BlockPos b, float width) {
        return arrow(new double3(a), new double3(b), width);
    }

    static VisibilityConfigurable arrow(BlockPos a, BlockPos b) {
        return arrow(new double3(a), new double3(b));
    }


    static VisibilityConfigurable text(Object text, double3 position, int colour) {
        return GizmoDrawing.text(text.toString(), position.toVec3d(), TextGizmo.Style.left(colour));
    }

    static VisibilityConfigurable text(Object text, double3 position) {
        return text(text, position, 0xffFFFFFF);
    }

    static VisibilityConfigurable text(Object text, BlockPos position, int colour) {
        return text(text, new double3(position), colour);
    }

    static VisibilityConfigurable text(Object text, BlockPos position) {
        return text(text, new double3(position));
    }
}