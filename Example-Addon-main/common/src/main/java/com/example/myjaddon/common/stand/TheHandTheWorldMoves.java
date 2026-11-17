package com.example.myjaddon.common.stand;

import com.example.myjaddon.common.register.SoundRegistry;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.api.attack.MoveMap;
import net.arna.jcraft.api.attack.StateContainer;
import net.arna.jcraft.api.attack.enums.MoveClass;
import net.arna.jcraft.api.component.living.CommonHitPropertyComponent;
import net.arna.jcraft.api.registry.JSoundRegistry;
import net.arna.jcraft.common.attack.moves.shared.GrabAttack;
import net.arna.jcraft.common.attack.moves.shared.KnockdownAttack;
import net.arna.jcraft.common.attack.moves.shared.MainBarrageAttack;
import net.arna.jcraft.common.attack.moves.shared.SimpleAttack;
import net.arna.jcraft.common.attack.moves.shared.SimpleMultiHitAttack;
import net.arna.jcraft.common.attack.moves.shared.SimpleUppercutAttack;
import net.arna.jcraft.common.attack.moves.shared.Stomp2Attack;
import net.arna.jcraft.common.attack.moves.shared.TimeSkipMove;
import net.arna.jcraft.common.attack.moves.shared.TimeStopMove;
import net.arna.jcraft.common.attack.moves.shared.TossChargeMove;
import net.arna.jcraft.common.attack.moves.shared.TossMove;
import net.arna.jcraft.common.attack.moves.thehand.EraseGroundAttack;
import net.arna.jcraft.common.attack.moves.thehand.SimpleEraseAttack;
import net.arna.jcraft.common.util.JParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;

/**
 * The Hand's move list with H and mouse 5 replaced.
 * H is JCraft's ultimate key. Mouse 5 is JCraft's utility key.
 */
public final class TheHandTheWorldMoves {
    public static final Stomp2Attack<TheHandTheWorldEntity> CROUCHING_LIGHT_FOLLOWUP = new Stomp2Attack<TheHandTheWorldEntity>(0,
            13, 20, 0.6f, 6f, 15, 1.75f, 0.3f, 0.4f, -0.3f)
            .withAnim(TheHandTheWorldEntity.State.CROUCHING_LIGHT_FOLLOWUP)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withImpactSound(JSoundRegistry.IMPACT_2)
            .withExtraHitBox(0, 0, 1)
            .withHitAnimation(CommonHitPropertyComponent.HitAnimation.CRUSH)
            .withHitSpark(JParticleType.HIT_SPARK_2)
            .withInfo(Component.literal("Stomp (Second Hit)"), Component.literal("Lifts knocked down enemies off the ground."));
    public static final SimpleAttack<TheHandTheWorldEntity> CROUCHING_LIGHT = new SimpleAttack<TheHandTheWorldEntity>(JCraft.LIGHT_COOLDOWN,
            9, 14, 0.5f, 5f, 15, 1.5f, 0.25f, 0.4f)
            .withFollowup(CROUCHING_LIGHT_FOLLOWUP)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitAnimation(CommonHitPropertyComponent.HitAnimation.LOW)
            .withBlockStun(12)
            .withInfo(Component.literal("Stomp"), Component.literal("Relatively quick combo starter."));
    public static final SimpleUppercutAttack<TheHandTheWorldEntity> LIGHT_FOLLOWUP = new SimpleUppercutAttack<TheHandTheWorldEntity>(
            0, 9, 14, 0.75f, 6f, 8, 1.6f, 0.3f, -0.1f, 0.3f)
            .withAnim(TheHandTheWorldEntity.State.LIGHT_FOLLOWUP)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withBlockStun(4)
            .withExtraHitBox(0, 0, 1)
            .withHitSpark(JParticleType.HIT_SPARK_2)
            .withInfo(Component.literal("Gut Punch"), Component.empty());
    public static final SimpleAttack<TheHandTheWorldEntity> LIGHT = new SimpleAttack<TheHandTheWorldEntity>(JCraft.LIGHT_COOLDOWN,
            5, 10, 0.75f, 4f, 12, 1.5f, 0.25f, -0.1f)
            .withFollowup(LIGHT_FOLLOWUP)
            .withCrouchingVariant(CROUCHING_LIGHT)
            .withImpactSound(JSoundRegistry.IMPACT_6)
            .withHitAnimation(CommonHitPropertyComponent.HitAnimation.HIGH)
            .noLoopPrevention()
            .withInfo(Component.literal("Punch"), Component.literal("Relatively quick combo starter."));
    public static final KnockdownAttack<TheHandTheWorldEntity> STOMP_BARRAGE_FINISHER = new KnockdownAttack<TheHandTheWorldEntity>(0,
            6, 10, 1.0f, 6f, 6, 1.75f, 0.5f, 0.3f, 35)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withImpactSound(JSoundRegistry.IMPACT_2)
            .withExtraHitBox(0, 0, 1)
            .withHitSpark(JParticleType.HIT_SPARK_2)
            .withInfo(Component.literal("Stomp Barrage (Last Hit)"), Component.literal("Lifts knocked down enemies off the ground."));
    public static final SimpleMultiHitAttack<TheHandTheWorldEntity> STOMP_BARRAGE = new SimpleMultiHitAttack<TheHandTheWorldEntity>(100,
            33, 1.0f, 1.0f, 10, 1.5f, 0.3f, 0.2f, IntSet.of(6, 9, 13, 16, 19, 22))
            .withSound(JSoundRegistry.THE_HAND_KICK_BARRAGE)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withFinisher(23, STOMP_BARRAGE_FINISHER)
            .withInfo(Component.literal("Stomp Barrage"), Component.literal("Fast startup. Last hit knocks down."));
    public static final MainBarrageAttack<TheHandTheWorldEntity> BARRAGE = new MainBarrageAttack<TheHandTheWorldEntity>(240, 0,
            40, 0.75f, 0.8f, 30, 2f, 0.25f, 0f, 3, Blocks.DEEPSLATE.defaultDestroyTime())
            .withSound(JSoundRegistry.D4C_BARRAGE)
            .withImpactSound(JSoundRegistry.IMPACT_2)
            .withInfo(Component.literal("Barrage"), Component.literal("fast reliable combo starter/extender, high stun"));
    public static final KnockdownAttack<TheHandTheWorldEntity> SWEEP = new KnockdownAttack<TheHandTheWorldEntity>(0, 13, 18, 1.0f,
            9f, 15, 1.6f, 0.4f, 0.3f, 35)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withAnim(TheHandTheWorldEntity.State.SWEEP)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withInfo(Component.literal("Sweep"), Component.literal("Can be comboed out of with cr.M1~M1>..."));
    public static final SimpleUppercutAttack<TheHandTheWorldEntity> KICK = new SimpleUppercutAttack<TheHandTheWorldEntity>(0, 13, 24, 0.75f,
            9f, 12, 2f, 1.1f, 0.1f, 0.3f)
            .withSound(JSoundRegistry.D4C_LIGHT)
            .withCrouchingVariant(SWEEP)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withHitSpark(JParticleType.HIT_SPARK_3)
            .withHyperArmor()
            .withLaunch()
            .withInfo(Component.literal("Home Run!"), Component.literal("Uninterruptible launcher."));
    public static final EraseGroundAttack<TheHandTheWorldEntity> ERASE_GROUND = new EraseGroundAttack<TheHandTheWorldEntity>(120, 18, 29, 0.75f,
            8.0f, 14, 2.0f, 0, 0.35f)
            .withSound(JSoundRegistry.THE_HAND_SWIPE)
            .withAnim(TheHandTheWorldEntity.State.ERASE_GROUND)
            .withImpactSound(JSoundRegistry.IMPACT_12)
            .withInfo(Component.literal("Erase"), Component.literal("Erases the ground in front of the user."))
            .withStaticY()
            .withArmor(2);
    public static final SimpleEraseAttack<TheHandTheWorldEntity> ERASE = new SimpleEraseAttack<TheHandTheWorldEntity>(120, 18, 29, 0.75f,
            8.0f, 14, 2.0f, 0, 0)
            .withSound(JSoundRegistry.THE_HAND_SWIPE)
            .withAnim(TheHandTheWorldEntity.State.ERASE)
            .withCrouchingVariant(ERASE_GROUND)
            .withImpactSound(JSoundRegistry.IMPACT_12)
            .withArmor(2)
            .withInfo(Component.literal("Erase"), Component.literal("Slow, unblockable attack."));
    public static final SimpleAttack<TheHandTheWorldEntity> GRAB_HIT = new SimpleAttack<TheHandTheWorldEntity>(0, 14, 16,
            0.75f, 8f, 5, 1.75f, 1.5f, 0f)
            .withImpactSound(JSoundRegistry.IMPACT_1)
            .withLaunch()
            .withHitSpark(JParticleType.HIT_SPARK_2)
            .withInfo(Component.literal("Grab (Hit)"), Component.empty());
    public static final GrabAttack<TheHandTheWorldEntity, TheHandTheWorldEntity.State> GRAB = new GrabAttack<>(300, 10, 20,
            0.75f, 0f, 16, 1.5f, 0f, 0f, GRAB_HIT, StateContainer.of(TheHandTheWorldEntity.State.GRAB_HIT))
            .withSound(JSoundRegistry.THE_HAND_GRAB)
            .withInfo(Component.literal("Grab"), Component.literal("unblockable, knocks back"));
    public static final TimeStopMove<TheHandTheWorldEntity> TIME_STOP = new TimeStopMove<TheHandTheWorldEntity>(
            TheHandTheWorldBalance.TIME_STOP_COOLDOWN,
            TheHandTheWorldBalance.TIME_STOP_WINDUP,
            TheHandTheWorldBalance.TIME_STOP_MOVE_DURATION,
            Either.left(TheHandTheWorldBalance.TIME_STOP_TICKS))
            .withSound(SoundRegistry.THE_HAND_WORLD_TIMESTOP)
            .withInfo(Component.literal("Timestop"), Component.literal("5 seconds"));
    public static final TimeSkipMove<TheHandTheWorldEntity> TIME_SKIP = new TimeSkipMove<TheHandTheWorldEntity>(
            TheHandTheWorldBalance.TIME_SKIP_COOLDOWN, TheHandTheWorldBalance.TIME_SKIP_BLOCKS, 2, 2, 1)
            .withSound(JSoundRegistry.TIME_SKIP)
            .withInfo(Component.literal("Timeskip"), Component.literal("14m range"));
    public static final TossMove<TheHandTheWorldEntity> TOSS = new TossMove<TheHandTheWorldEntity>(0, 1, 1, 0.75f, 0.13f)
            .withAnim(TheHandTheWorldEntity.State.ITEM_TOSS);
    public static final TossChargeMove<TheHandTheWorldEntity> TOSS_CHARGE = new TossChargeMove<TheHandTheWorldEntity>(70, 21, 40, 1.0f, 10)
            .withFollowup(TOSS);

    private TheHandTheWorldMoves() {
    }

    /**
     * Puts The Hand's attacks on this stand, then maps H to timestop and mouse 5 to timeskip.
     *
     * @param moves move map being built for the stand
     */
    public static void register(MoveMap<TheHandTheWorldEntity, TheHandTheWorldEntity.State> moves) {
        var light = moves.register(MoveClass.LIGHT, LIGHT, TheHandTheWorldEntity.State.LIGHT);
        light.withFollowup(TheHandTheWorldEntity.State.LIGHT_FOLLOWUP);
        light.withCrouchingVariant(TheHandTheWorldEntity.State.CROUCHING_LIGHT)
                .withFollowup(TheHandTheWorldEntity.State.CROUCHING_LIGHT_FOLLOWUP);
        moves.registerImmediate(MoveClass.HEAVY, KICK, TheHandTheWorldEntity.State.KICK);
        moves.register(MoveClass.BARRAGE, BARRAGE, TheHandTheWorldEntity.State.BARRAGE);
        moves.registerImmediate(MoveClass.SPECIAL1, ERASE, TheHandTheWorldEntity.State.ERASE);
        moves.register(MoveClass.SPECIAL2, STOMP_BARRAGE, TheHandTheWorldEntity.State.STOMP_BARRAGE);
        moves.registerImmediate(MoveClass.SPECIAL3, GRAB, TheHandTheWorldEntity.State.GRAB);
        moves.register(MoveClass.ULTIMATE, TIME_STOP, TheHandTheWorldEntity.State.TIME_STOP);
        moves.register(MoveClass.UTILITY, TIME_SKIP, TheHandTheWorldEntity.State.TIME_SKIP);
        moves.register(MoveClass.TOSS, TOSS_CHARGE, TheHandTheWorldEntity.State.ITEM_TOSS_CHARGE)
                .withFollowup(TheHandTheWorldEntity.State.ITEM_TOSS);
    }
}
