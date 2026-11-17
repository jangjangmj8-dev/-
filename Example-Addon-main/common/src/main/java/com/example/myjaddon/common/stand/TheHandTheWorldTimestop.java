package com.example.myjaddon.common.stand;

import com.example.myjaddon.ExampleAddon;
import com.example.myjaddon.common.register.SoundRegistry;
import net.arna.jcraft.common.network.s2c.ShaderActivationPacket;
import net.arna.jcraft.common.tickable.Timestops;
import net.arna.jcraft.platform.JComponentPlatformUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Extends JCraft's timestop from its 96-block box out to this stand's 100-block box.
 */
public final class TheHandTheWorldTimestop {
    private TheHandTheWorldTimestop() {
    }

    /**
     * Freezes entities in the 100-block box and, on the first tick, gives the outer shell the grayscale shader.
     *
     * @param stand     stand that stopped time
     * @param duration  timestop length in ticks
     * @param firstTick whether this is the tick the stop began
     */
    public static void extend(TheHandTheWorldEntity stand, int duration, boolean firstTick) {
        if (!(stand.level() instanceof ServerLevel world)) {
            ExampleAddon.LOGGER.warn("Skipped timestop extension because {} is not on a server level", stand);
            return;
        }
        Entity user = stand.getUser();
        Vec3 pos = stand.position();
        double radius = TheHandTheWorldBalance.TIME_STOP_RADIUS_BLOCKS;
        AABB box = new AABB(pos.add(radius, radius, radius), pos.subtract(radius, radius, radius));
        for (Entity entity : world.getEntitiesOfClass(Entity.class, box, Timestops.TIMESTOP_PREDICATE)) {
            if (entity == user || entity == stand || entity.isPassenger() || user != null && user.isPassengerOfSameVehicle(entity)) {
                continue;
            }
            JComponentPlatformUtils.getTimeStopData(entity).ifPresent(data -> data.setTicks(2));
            if (firstTick && entity instanceof ServerPlayer player && inOuterShell(pos, player.position())) {
                ShaderActivationPacket.send(player, user == null ? stand : user, 0, duration, ShaderActivationPacket.Type.ZA_WARUDO);
                freezeHeldItems(player, duration);
            }
        }
    }

    /**
     * Plays the configurable resume sound. The default clip is JCraft's timestop sound.
     *
     * @param stand stand whose timestop just ended
     */
    public static void playResume(TheHandTheWorldEntity stand) {
        if (!(stand.level() instanceof ServerLevel world)) {
            ExampleAddon.LOGGER.warn("Skipped time-resume sound because {} is not on a server level", stand);
            return;
        }
        world.playSound(null, stand.getX(), stand.getY(), stand.getZ(),
                SoundRegistry.THE_HAND_WORLD_TIMERESUME.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    private static boolean inOuterShell(Vec3 origin, Vec3 target) {
        return TheHandTheWorldRange.inOuterBox(
                target.x - origin.x, target.y - origin.y, target.z - origin.z,
                TheHandTheWorldBalance.JCRAFT_TIMESTOP_BOX_BLOCKS,
                TheHandTheWorldBalance.TIME_STOP_RADIUS_BLOCKS);
    }

    private static void freezeHeldItems(ServerPlayer player, int duration) {
        for (int i = 0; i < player.getInventory().items.size(); i++) {
            player.getCooldowns().addCooldown(player.getInventory().items.get(i).getItem(), duration);
        }
        player.getCooldowns().addCooldown(player.getOffhandItem().getItem(), duration);
    }
}
