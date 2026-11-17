package com.example.myjaddon.common.stand;

import com.example.myjaddon.ExampleAddon;
import com.example.myjaddon.common.register.SoundRegistry;
import com.example.myjaddon.common.register.StandTypeRegistry;
import lombok.NonNull;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.api.Attacks;
import net.arna.jcraft.api.attack.MoveSet;
import net.arna.jcraft.api.attack.MoveSetManager;
import net.arna.jcraft.api.attack.enums.MoveClass;
import net.arna.jcraft.api.attack.enums.MoveInputType;
import net.arna.jcraft.api.attack.moves.AbstractMove;
import net.arna.jcraft.api.stand.StandData;
import net.arna.jcraft.api.stand.StandEntity;
import net.arna.jcraft.api.stand.StandInfo;
import net.arna.jcraft.api.stand.SummonData;
import net.arna.jcraft.common.util.StandAnimationState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

/**
 * Standalone stand that keeps The Hand's body and attacks, and uses The World's time.
 * JCraft's The Hand is a different stand type and is left unchanged.
 */
public class TheHandTheWorldEntity extends StandEntity<TheHandTheWorldEntity, TheHandTheWorldEntity.State> {
    public static final MoveSet<TheHandTheWorldEntity, State> MOVE_SET = MoveSetManager.create(
            StandTypeRegistry.THE_HAND_THE_WORLD,
            TheHandTheWorldMoves::register,
            TheHandTheWorldEntity.class,
            State.class);
    public static final StandData DATA = StandData.builder()
            .info(StandInfo.builder()
                    .name(Component.translatable("entity.myjaddon.the_hand_the_world"))
                    .proCount(4)
                    .conCount(2)
                    .freeSpace(Component.translatable("entity.myjaddon.the_hand_the_world.desc"))
                    .skinName(Component.literal("Shift"))
                    .skinName(Component.literal("Inversion"))
                    .skinName(Component.literal("Deletion"))
                    .build())
            .summonData(SummonData.of(SoundRegistry.THE_HAND_WORLD_SUMMON))
            .build();

    private boolean timeStopActive;

    public TheHandTheWorldEntity(Level world) {
        super(StandTypeRegistry.THE_HAND_THE_WORLD.get(), world);
        auraColors = new Vector3f[]{
                new Vector3f(0, 0, 1.0f),
                new Vector3f(0.6f, 0.2f, 0f),
                new Vector3f(0.8f, 0.2f, 0.8f),
                new Vector3f(0.2f, 0, 0.5f),
        };
    }

    /**
     * Keeps the extra 100-block freeze running, then plays the resume sound when time returns.
     */
    @Override
    public void tick() {
        int before = tsTime;
        super.tick();
        if (level().isClientSide()) {
            return;
        }
        if (before <= 0 && tsTime > 0) {
            timeStopActive = true;
            TheHandTheWorldTimestop.extend(this, tsTime, true);
            ExampleAddon.LOGGER.info("The Hand: The World stopped time for {} ticks", tsTime);
        } else if (timeStopActive && tsTime > 0) {
            TheHandTheWorldTimestop.extend(this, tsTime, false);
        } else if (timeStopActive && tsTime <= 0) {
            timeStopActive = false;
            TheHandTheWorldTimestop.playResume(this);
            ExampleAddon.LOGGER.info("The Hand: The World resumed time");
        }
    }

    /**
     * Repeats erasure the way The Hand does, and chains light follow-ups.
     *
     * @param moveClass input that was just pressed
     * @return true when this method consumed the input
     */
    @Override
    public boolean initMove(MoveClass moveClass) {
        if (tryFollowUp(moveClass, MoveClass.LIGHT)) {
            return true;
        }
        if (moveClass == MoveClass.SPECIAL1 && getCurrentMove() != null
                && getCurrentMove().getMoveClass() == MoveClass.SPECIAL1 && getMoveStun() < 4) {
            AbstractMove<?, ? super TheHandTheWorldEntity> repeat = getCurrentMove();
            if (repeat != null) {
                setMove(repeat, (State) repeat.getAnimation());
                return true;
            }
        }
        return super.initMove(moveClass);
    }

    /**
     * Lets erasure be cancelled into itself instead of queueing a second press.
     *
     * @param type input currently held
     */
    @Override
    public void queueMove(MoveInputType type) {
        if (type == MoveInputType.SPECIAL1 && getCurrentMove() != null
                && getCurrentMove().getMoveClass() == MoveClass.SPECIAL1) {
            return;
        }
        super.queueMove(type);
    }

    /**
     * Ground erasure stays on the floor instead of using the normal stand height offset.
     *
     * @return false while the ground-erase pose is playing
     */
    @Override
    public boolean shouldOffsetHeight() {
        if (getState() == State.ERASE_GROUND) {
            return false;
        }
        return super.shouldOffsetHeight();
    }

    @Override
    public @NonNull TheHandTheWorldEntity getThis() {
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

    /**
     * The Hand animation names, plus The World's timestop pose retargeted onto this file.
     */
    public enum State implements StandAnimationState<TheHandTheWorldEntity> {
        IDLE(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.idle", AzPlayBehaviors.LOOP)),
        LIGHT(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.light", AzPlayBehaviors.PLAY_ONCE)),
        LIGHT_FOLLOWUP(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.light2", AzPlayBehaviors.PLAY_ONCE)),
        CROUCHING_LIGHT(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.crouching_light", AzPlayBehaviors.PLAY_ONCE)),
        CROUCHING_LIGHT_FOLLOWUP(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.crouching_light2", AzPlayBehaviors.PLAY_ONCE)),
        BLOCK(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.block", AzPlayBehaviors.LOOP)),
        KICK(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.heavy", AzPlayBehaviors.PLAY_ONCE)),
        BARRAGE(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.barrage", AzPlayBehaviors.LOOP)),
        ERASE(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.erase", AzPlayBehaviors.PLAY_ONCE)),
        ERASE_GROUND(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.erase_ground", AzPlayBehaviors.PLAY_ONCE)),
        SWEEP(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.sweep", AzPlayBehaviors.LOOP)),
        GRAB(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.grab", AzPlayBehaviors.PLAY_ONCE)),
        GRAB_HIT(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.grab_hit", AzPlayBehaviors.PLAY_ONCE)),
        STOMP_BARRAGE(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.stomp_barrage", AzPlayBehaviors.PLAY_ONCE)),
        TIME_STOP(Attacks.createAnimationCommand(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.timestop", AzPlayBehaviors.PLAY_ONCE)),
        TIME_SKIP(AzCommand.create(ExampleAddon.BASE_CONTROLLER, "animation.the_hand.idle", AzPlayBehaviors.LOOP)),
        ITEM_TOSS_CHARGE(Attacks.createAnimationCommand(JCraft.BASE_CONTROLLER, "itemthrow_charge", AzPlayBehaviors.PLAY_ONCE)),
        ITEM_TOSS(Attacks.createAnimationCommand(JCraft.BASE_CONTROLLER, "itemthrow", AzPlayBehaviors.PLAY_ONCE));

        private final AzCommand animator;

        State(AzCommand animator) {
            this.animator = animator;
        }

        @Override
        public void playAnimation(TheHandTheWorldEntity attacker) {
            animator.sendForEntity(attacker);
        }
    }
}
