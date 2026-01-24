package net.collectively.geode.mc.util;

import net.collectively.geode.core.math;
import net.collectively.geode.core.types.double3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface WorldUtil {
    static void addParticleClient(
            World world,
            ParticleEffect effect,
            double3 position,
            double3 velocity,
            int count
    ) {
        for (int i = 0; i < count; i++) {
            world.addParticleClient(
                    effect,
                    position.x(), position.y(), position.z(),
                    velocity.x(), velocity.y(), velocity.z()
            );
        }
    }

    static void addParticleClient(
            World world,
            ParticleEffect effect,
            double3 position,
            double3 velocity
    ) {
        addParticleClient(world, effect, position, velocity, 1);
    }

    static void createExplosion(Entity entity, float power, World.ExplosionSourceType type) {
        createExplosion(entity.getEntityWorld(), entity, new double3(entity.getEntityPos()), power, type);
    }

    static void createExplosion(Entity entity, double3 pos, float power, World.ExplosionSourceType type) {
        createExplosion(entity.getEntityWorld(), entity, pos, power, type);
    }

    static void createExplosion(World world, @Nullable Entity entity, double3 pos, float power, World.ExplosionSourceType type) {
        world.createExplosion(entity, pos.x(), pos.y(), pos.z(), power, type);
    }

    static @Nullable Entity raymarchToClosest(World world,
                                              Entity sourceEntity,
                                              double3 startPosition,
                                              int stepCount,
                                              double stepSize,
                                              double baseRadius,
                                              double stepRadius) {
        double3 forward = new double3(sourceEntity.getRotationVector());

        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < stepCount; i++) {
            double3 pos = startPosition.add(forward.mul(stepSize * i));
            double radius = baseRadius + i * stepRadius;

            List<Entity> entities = EntityHelper.getEntitiesAround(sourceEntity, world, pos, radius);
            for (Entity entity : entities) {
                double distance = entity.getEntityPos().distanceTo(pos.toVec3d());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        return closestEntity;
    }

    static List<Entity> raymarch(World world,
                                 Entity entity,
                                 double3 startPosition,
                                 int stepCount,
                                 double stepSize,
                                 double baseRadius,
                                 double stepRadius) {

        List<Entity> entities = new ArrayList<>();
        double3 forward = new double3(entity.getRotationVector());
        for (int i = 0; i < stepCount; i++) {
            double3 pos = startPosition.add(forward.mul(stepSize * i));
            double radius = baseRadius + i * stepRadius;
            entities.addAll(EntityHelper.getEntitiesAround(entity, world, pos, radius));
        }

        return entities;
    }

    /// WARNING BROKEN
    static HitResult raycast(World world, Entity entity, double3 startPosition, double3 direction, double distance, Predicate<Entity> validate) {
        double squareDistance = distance * distance;
        double3 endPosition = startPosition.add(direction.mul(distance));
        BlockHitResult blockHitResult = world.raycast(new RaycastContext(
                startPosition.toVec3d(),
                endPosition.toVec3d(),
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                entity
        ));

        double blockHitDistance = blockHitResult.getPos().squaredDistanceTo(startPosition.toVec3d());
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            squareDistance = blockHitDistance;
            distance = math.sqrt(blockHitDistance);
        }

        Box hitbox = entity.getBoundingBox().stretch(direction.mul(distance).toVec3d()).expand(1);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                entity,
                startPosition.toVec3d(),
                endPosition.toVec3d(),
                hitbox,
                EntityPredicates.CAN_HIT.and(validate),
                squareDistance
        );

        if (entityHitResult == null
                || entityHitResult.getType() == HitResult.Type.MISS
                || entityHitResult.getPos().squaredDistanceTo(startPosition.toVec3d()) > blockHitDistance) {
            return blockHitResult;
        }

        return entityHitResult;
    }

    /// WARNING BROKEN
    static HitResult raycast(Entity entity, double distance, Predicate<Entity> validate) {
        return raycast(
                entity.getEntityWorld(),
                entity,
                new double3(entity.getEyePos()),
                new double3(entity.getRotationVector()),
                distance,
                validate
        );
    }

    static HitResult raycast(Entity entity, double distance) {
        return raycast(entity, distance, hit -> true);
    }
}
