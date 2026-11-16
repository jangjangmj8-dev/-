package com.example.myjaddon.common.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.arna.jcraft.api.attack.IAttacker;
import net.arna.jcraft.api.attack.core.MoveAction;
import net.arna.jcraft.api.attack.core.MoveActionType;
import net.arna.jcraft.api.attack.core.RunMoment;
import net.minecraft.world.entity.LivingEntity;

import java.util.Set;

/**
 * A move action that triggers The World's Time Stop ability on the stand entity.
 * When performed, it calls {@link ExampleStandEntity#startTimeStop()} on the attacker.
 * <p>
 * The run moment is set to {@link RunMoment#AT_INIT} so the time stop begins
 * immediately when the move is activated.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeStopAction extends MoveAction<TimeStopAction, IAttacker<?, ?>> {

    public static TimeStopAction timeStop() {
        TimeStopAction action = new TimeStopAction();
        action.setRunMoment(RunMoment.AT_INIT);
        return action;
    }

    private TimeStopAction() {
        super();
    }

    @Override
    public void perform(IAttacker<?, ?> attacker, LivingEntity user, Set<LivingEntity> targets) {
        if (attacker instanceof com.example.myjaddon.common.stand.ExampleStandEntity stand) {
            stand.startTimeStop();
        }
    }

    @Override
    public @NonNull MoveActionType<TimeStopAction> getType() {
        return Type.INSTANCE;
    }

    public static class Type extends MoveActionType<TimeStopAction> {
        public static final Type INSTANCE = new Type();

        @Override
        public Codec<TimeStopAction> getCodec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    runMoment()
            ).apply(instance, apply(TimeStopAction::timeStop)));
        }
    }
}
