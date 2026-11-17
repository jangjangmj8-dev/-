package com.example.myjaddon.common.register;

import com.example.myjaddon.ExampleAddon;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.arna.jcraft.api.JRegistries;
import net.arna.jcraft.api.stand.StandType;

public interface StandTypeRegistry {
    DeferredRegister<StandType> REGISTRY = DeferredRegister.create(ExampleAddon.MOD_ID, JRegistries.STAND_TYPE_REGISTRY_KEY);

    RegistrySupplier<StandType> EXAMPLE_STAND = REGISTRY.register(ExampleAddon.id("example_stand"), () ->
            StandType.of(ExampleAddon.id("example_stand"), EntityTypeRegistry.EXAMPLE_STAND));

    RegistrySupplier<StandType> THE_HAND_THE_WORLD = REGISTRY.register(ExampleAddon.id("the_hand_the_world"), () ->
            StandType.of(ExampleAddon.id("the_hand_the_world"), EntityTypeRegistry.THE_HAND_THE_WORLD));
}
