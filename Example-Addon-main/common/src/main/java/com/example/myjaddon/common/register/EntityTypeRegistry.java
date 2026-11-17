package com.example.myjaddon.common.register;

import com.example.myjaddon.ExampleAddon;
import com.example.myjaddon.common.stand.ExampleStandEntity;
import com.example.myjaddon.common.stand.TheHandTheWorldEntity;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public interface EntityTypeRegistry {
    DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ExampleAddon.MOD_ID, Registries.ENTITY_TYPE);

    RegistrySupplier<EntityType<ExampleStandEntity>> EXAMPLE_STAND = REGISTRY.register("example_stand", () ->
            EntityType.Builder.of((EntityType.EntityFactory<ExampleStandEntity>)
                            (t, level) -> new ExampleStandEntity(level),
                            MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("example_stand"));

    RegistrySupplier<EntityType<TheHandTheWorldEntity>> THE_HAND_THE_WORLD = REGISTRY.register("the_hand_the_world", () ->
            EntityType.Builder.of((EntityType.EntityFactory<TheHandTheWorldEntity>)
                            (t, level) -> new TheHandTheWorldEntity(level),
                            MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("the_hand_the_world"));

    /**
     * Registers default mob attributes for every stand entity in this addon.
     */
    static void registerAttributes() {
        EntityAttributeRegistry.register(EXAMPLE_STAND, ExampleStandEntity::createMobAttributes);
        EntityAttributeRegistry.register(THE_HAND_THE_WORLD, TheHandTheWorldEntity::createMobAttributes);
    }
}
