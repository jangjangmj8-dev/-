package com.example.myjaddon.client.register;

import com.example.myjaddon.client.renderer.ExampleStandRenderer;
import com.example.myjaddon.client.renderer.TheHandTheWorldRenderer;
import com.example.myjaddon.common.register.EntityTypeRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public interface EntityRendererRegister {
    /**
     * Registers every stand renderer in this addon.
     *
     * @param registrar platform-specific renderer registrar
     */
    static void register(EntityRendererRegistrar registrar) {
        registrar.register(EntityTypeRegistry.EXAMPLE_STAND, ExampleStandRenderer::new);
        registrar.register(EntityTypeRegistry.THE_HAND_THE_WORLD, TheHandTheWorldRenderer::new);
    }

    @FunctionalInterface
    interface EntityRendererRegistrar {
        <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type,
                                         EntityRendererProvider<T> rendererProvider);
    }
}
