package com.example.myjaddon.common.attack;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.arna.jcraft.api.attack.IAttacker;
import net.arna.jcraft.api.attack.MoveType;
import net.arna.jcraft.api.attack.core.RunMoment;
import net.arna.jcraft.api.attack.moves.AbstractMove;
import org.jetbrains.annotations.NotNull;

/**
 * A custom move for The Hand's H key (utility slot) that triggers The World's Time Stop ability.
 * Unlike standard attacks, this move does not deal damage - it freezes time for all entities
 * except the stand user, allowing free movement and combo setup.
 * <p>
 * This extends {@link AbstractMove} directly (not {@link net.arna.jcraft.api.attack.moves.AbstractSimpleAttack})
 * because time-stop moves do not have a hitbox, as noted in JCraft's API documentation.
 *
 * @param <A> the attacker type (stand entity)
 */
public class TimeStopMove<A extends IAttacker<? extends A, ?>> extends AbstractMove<TimeStopMove<A>, A> {

    public TimeStopMove(int cooldown, int windup, int duration, float moveDistance) {
        super(cooldown, windup, duration, moveDistance);
        // Register the time stop action to run at the start of the move (AT_INIT)
        TimeStopAction timeStopAction = TimeStopAction.timeStop();
        timeStopAction.setRunMoment(RunMoment.AT_INIT);
        getActions().add(timeStopAction);
    }

    @Override
    public @NotNull MoveType<TimeStopMove<A>> getMoveType() {
        return Type.INSTANCE.cast();
    }

    @Override
    protected @NotNull TimeStopMove<A> getThis() {
        return this;
    }

    @Override
    public @NotNull TimeStopMove<A> copy() {
        return copyExtras(new TimeStopMove<>(getCooldown(), getWindup(), getDuration(), getMoveDistance()));
    }

    public static class Type extends AbstractMove.Type<TimeStopMove<?>> {
        public static final Type INSTANCE = new Type();

        @Override
        protected @NotNull App<RecordCodecBuilder.Mu<TimeStopMove<?>>, TimeStopMove<?>> buildCodec(
                RecordCodecBuilder.Instance<TimeStopMove<?>> instance) {
            return instance.group(
                    extras(),
                    cooldown(),
                    windup(),
                    duration(),
                    moveDistance()
            ).apply(instance, TimeStopMove::new);
        }
    }
}