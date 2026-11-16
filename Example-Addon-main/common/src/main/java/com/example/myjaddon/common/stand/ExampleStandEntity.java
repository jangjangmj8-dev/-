package com.example.myjaddon.common.stand;

import com.example.myjaddon.ExampleAddon;
import com.example.myjaddon.common.attack.TimeStopMove;
import com.example.myjaddon.common.register.StandTypeRegistry;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.api.attack.MoveMap;
import net.arna.jcraft.api.attack.MoveSet;
import net.arna.jcraft.api.attack.MoveSetManager;
import net.arna.jcraft.api.attack.enums.MoveClass;
import net.arna.jcraft.api.registry.JSoundRegistry;
import net.arna.jcraft.api.stand.StandData;
import net.arna.jcraft.api.stand.StandEntity;
import net.arna.jcraft.api.stand.StandInfo;
import net.arna.jcraft.common.attack.moves.shared.MainBarrageAttack;
import net.arna.jcraft.common.attack.moves.shared.SimpleAttack;
import net.arna.jcraft.common.util.StandAnimationState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class ExampleStandEntity extends StandEntity<ExampleStandEntity, ExampleStandEntity.State> {
    public static final MoveSet<ExampleStandEntity, State> MOVE_SET = MoveSetManager.create(StandTypeRegistry.EXAMPLE_STAND,
            ExampleStandEntity::registerMoves, State.class);
    public static final StandData DATA = StandData.of(StandInfo.of(Component.literal("Example Stand")));

    public static final SimpleAttack<ExampleStandEntity> LIGHT = new SimpleAttack<ExampleStandEntity>(JCraft.LIGHT_COOLDOWN,
            5, 8, 0.75f, 5f, 16, 1.5f, 0.2f, -0.1f)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withInfo(
                    Component.literal("Punch"),
                    Component.literal("An example punch attack")
            );
    public static final MainBarrageAttack<ExampleStandEntity> BARRAGE = new MainBarrageAttack<ExampleStandEntity>(280,
            0, 40, 0.75f, 1f, 30, 2f, 0.25f, 0f, 3, Blocks.OBSIDIAN.defaultDestroyTime())
            .withSound(JSoundRegistry.STAR_PLATINUM_BARRAGE)
            .withInfo(
                    Component.translatable("jcraft.generic.barrage"),
                    Component.literal("An example barrage attack")
            );

    /**
     * The World's Time Stop - assigned to the H key (utility slot).
     * Freezes all entities around the user for a set duration, allowing free movement
     * and combo setup, then resumes time with accumulated impacts.
     */
    public static final TimeStopMove<ExampleStandEntity> TIME_STOP_MOVE = new TimeStopMove<>(
            400, // cooldown: 20 seconds (400 ticks)
            10,  // windup: 0.5 seconds (10 ticks)
            200, // duration: 10 seconds (200 ticks)
            0f   // move distance: none (area effect)
    );

    public static final int TIME_STOP_DURATION = 200; // 10 seconds at 20 TPS

    private int timeStopTicks = 0;

    public ExampleStandEntity(Level world) {
        super(StandTypeRegistry.EXAMPLE_STAND.get(), world);
    }

    /**
     * Called by the time stop move action when the H key is pressed.
     * Starts the time stop effect.
     */
    public void startTimeStop() {
        this.timeStopTicks = TIME_STOP_DURATION;
        this.level().playSound(null, this.blockPosition(), JSoundRegistry.STAR_PLATINUM_BARRAGE,
                net.minecraft.sounds.SoundSource.HOSTILE, 1.0f, 0.5f);
    }

    @Override
    public void tick() {
        super.tick();

        if (timeStopTicks > 0) {
            freezeEntities();
            timeStopTicks--;
            if (timeStopTicks == 0) {
                resumeTime();
            }
        }
    }

    /**
     * Freezes all nearby entities except the stand itself and its user.
     * Entities are frozen by zeroing their movement each tick.
     */
    private void freezeEntities() {
        Level level = this.level();
        if (level == null || level.isClientSide()) {
            return;
        }

        Player user = this.getUser();
        if (user == null) {
            return;
        }
        double radius = 25.0;

        level.getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(radius))
                .forEach(entity -> {
                    if (entity == this || entity == user) {
                        return;
                    }

                    entity.setDeltaMovement(0, 0, 0);

                    if (entity instanceof Mob mob) {
                        mob.getNavigation().stop();
                    }
                });
    }

    /**
     * Called when time stop ends. Time resumes naturally.
     */
    private void resumeTime() {
        this.level().playSound(null, this.blockPosition(), JSoundRegistry.STAR_PLATINUM_BARRAGE,
                net.minecraft.sounds.SoundSource.HOSTILE, 1.0f, 1.2f);
    }

    private static void registerMoves(MoveMap<ExampleStandEntity, State> moveMap) {
        moveMap.register(MoveClass.LIGHT, LIGHT, State.LIGHT);
        moveMap.register(MoveClass.HEAVY, LIGHT, State.LIGHT);
        moveMap.register(MoveClass.BARRAGE, BARRAGE, State.BARRAGE);
        // H key / utility slot: The World's Time Stop
        moveMap.register(MoveClass.UTILITY, TIME_STOP_MOVE, State.TIME_STOP);
    }

    @NotNull
    @Override
    public ExampleStandEntity getThis() {
        return this;
    }

    @Override
    protected State[] getStateValues() {
        return State.values();
    }

    @Override
    public State getBlockState() {
        return State.BLOCK;
    }

    public enum State implements StandAnimationState<ExampleStandEntity> {
        IDLE(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "idle", AzPlayBehaviors.LOOP)),
        LIGHT(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "light", AzPlayBehaviors.HOLD_ON_LAST_FRAME)),
        BLOCK(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "block", AzPlayBehaviors.LOOP)),
        BARRAGE(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "barrage", AzPlayBehaviors.LOOP)),
        TIME_STOP(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "idle", AzPlayBehaviors.LOOP));

        private final AzCommand animator;

        State(AzCommand animator) {
            this.animator = animator;
        }

        @Override
        public void playAnimation(ExampleStandEntity attacker) {
            animator.sendForEntity(attacker);
        }
    }
}
