package com.example.myjaddon.common.register;

import com.example.myjaddon.ExampleAddon;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

/**
 * Sound events for The Hand: The World.
 * Drop matching {@code .ogg} files in {@code assets/myjaddon/sounds/}.
 */
public interface SoundRegistry {
    DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ExampleAddon.MOD_ID, Registries.SOUND_EVENT);

    /** Played when this stand is summoned. File: {@code sounds/the_hand_world_summon.ogg}. */
    RegistrySupplier<SoundEvent> THE_HAND_WORLD_SUMMON = REGISTRY.register("the_hand_world_summon",
            () -> SoundEvent.createVariableRangeEvent(ExampleAddon.id("the_hand_world_summon")));

    /** Played when H starts the timestop. File: {@code sounds/the_hand_world_timestop.ogg}. */
    RegistrySupplier<SoundEvent> THE_HAND_WORLD_TIMESTOP = REGISTRY.register("the_hand_world_timestop",
            () -> SoundEvent.createVariableRangeEvent(ExampleAddon.id("the_hand_world_timestop")));

    /**
     * Played when the 100-tick stop ends.
     * {@code sounds.json} points this at JCraft's timestop clip until a custom file replaces it.
     */
    RegistrySupplier<SoundEvent> THE_HAND_WORLD_TIMERESUME = REGISTRY.register("the_hand_world_timeresume",
            () -> SoundEvent.createVariableRangeEvent(ExampleAddon.id("the_hand_world_timeresume")));
}
