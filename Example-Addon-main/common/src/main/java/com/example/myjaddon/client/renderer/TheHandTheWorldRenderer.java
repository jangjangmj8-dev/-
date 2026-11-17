package com.example.myjaddon.client.renderer;

import com.example.myjaddon.common.register.StandTypeRegistry;
import com.example.myjaddon.common.stand.TheHandTheWorldEntity;
import net.arna.jcraft.client.renderer.entity.stands.StandEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders this stand with The Hand's model and textures.
 * The animation file is this addon's copy, which adds the timestop pose.
 */
@Environment(EnvType.CLIENT)
public class TheHandTheWorldRenderer extends StandEntityRenderer<TheHandTheWorldEntity> {
    private static final ResourceLocation MODEL = new ResourceLocation("jcraft", "geo/the_hand.geo.json");

    /**
     * Builds a renderer that borrows The Hand's geo and skin textures.
     *
     * @param context renderer context supplied by Minecraft
     */
    public TheHandTheWorldRenderer(EntityRendererProvider.Context context) {
        super(context,
                builder -> builder
                        .setRenderType(stand -> StandEntityRenderer.renderTypeOf(stand, texture(stand)))
                        .addRenderLayer(new StandEntityRenderer.StandHandItemsRenderLayer<>())
                        .addRenderLayer(new TheHandTheWorldErasureLayer()),
                stand -> MODEL,
                TheHandTheWorldRenderer::texture,
                StandTypeRegistry.THE_HAND_THE_WORLD.get(),
                false, false, 0f, 0f, 90f);
    }

    /**
     * Picks The Hand skin that matches the stand's skin index.
     *
     * @param stand stand being drawn
     * @return texture inside the JCraft jar
     */
    public static ResourceLocation texture(TheHandTheWorldEntity stand) {
        int skin = Math.min(stand.getSkin(), 3);
        String file = skin == 0 ? "default" : "skin" + skin;
        return new ResourceLocation("jcraft", "textures/entity/stands/the_hand/" + file + ".png");
    }
}
package com.example.myjaddon.client.renderer;

import com.example.myjaddon.common.register.StandTypeRegistry;
import com.example.myjaddon.common.stand.TheHandTheWorldEntity;
import net.arna.jcraft.client.renderer.entity.stands.StandEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders this stand with The Hand's model and textures.
 * The animation file is this addon's copy, which adds the timestop pose.
 */
@Environment(EnvType.CLIENT)
public class TheHandTheWorldRenderer extends StandEntityRenderer<TheHandTheWorldEntity> {
    private static final ResourceLocation MODEL = new ResourceLocation("jcraft", "geo/the_hand.geo.json");

    /**
     * Builds a renderer that borrows The Hand's geo and skin textures.
     *
     * @param context renderer context supplied by Minecraft
     */
    public TheHandTheWorldRenderer(EntityRendererProvider.Context context) {
        super(context,
                builder -> builder
                        .setRenderType(stand -> StandEntityRenderer.renderTypeOf(stand, texture(stand)))
                        .addRenderLayer(new StandEntityRenderer.StandHandItemsRenderLayer<>())
                        .addRenderLayer(new TheHandTheWorldErasureLayer()),
                stand -> MODEL,
                TheHandTheWorldRenderer::texture,
                StandTypeRegistry.THE_HAND_THE_WORLD.get(),
                false, false, 0f, 0f, 90f);
    }

    /**
     * Picks The Hand skin that matches the stand's skin index.
     *
     * @param stand stand being drawn
     * @return texture inside the JCraft jar
     */
    public static ResourceLocation texture(TheHandTheWorldEntity stand) {
        int skin = Math.min(stand.getSkin(), 3);
        String file = skin == 0 ? "default" : "skin" + skin;
        return new ResourceLocation("jcraft", "textures/entity/stands/the_hand/" + file + ".png");
    }
}
