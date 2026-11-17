package com.example.myjaddon;

import com.example.myjaddon.common.register.EntityTypeRegistry;
import com.example.myjaddon.common.register.SoundRegistry;
import com.example.myjaddon.common.register.StandTypeRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class ExampleAddon {
    public static final String MOD_ID = "myjaddon";
    public static final String BASE_CONTROLLER = "base_controller";
    /** Shared logger for addon registration and timestop events. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Registers entities, stand types, sounds, and mob attributes.
     */
    public static void init() {
        EntityTypeRegistry.REGISTRY.register();
        StandTypeRegistry.REGISTRY.register();
        SoundRegistry.REGISTRY.register();
        EntityTypeRegistry.registerAttributes();
        LOGGER.info("Registered The Hand: The World");
    }

    /**
     * Builds a resource location in the {@code myjaddon} namespace.
     *
     * @param path path inside the namespace
     * @return the resource location
     */
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
