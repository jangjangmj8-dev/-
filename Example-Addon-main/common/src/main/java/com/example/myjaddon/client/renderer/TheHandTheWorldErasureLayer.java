package com.example.myjaddon.client.renderer;

import com.example.myjaddon.common.stand.TheHandTheWorldEntity;
import lombok.NonNull;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.client.renderer.entity.layer.AbstractRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * The Hand's erasure glow, drawn with JCraft's erase textures.
 */
@Environment(EnvType.CLIENT)
public class TheHandTheWorldErasureLayer extends AbstractRenderLayer<TheHandTheWorldEntity> {
    private static final List<ResourceLocation> SKINS = IntStream.range(0, 4)
            .mapToObj(i -> JCraft.id("textures/entity/stands/the_hand/erase" + i + ".png"))
            .toList();
    private static final List<ResourceLocation> SKINS_OUTER = IntStream.range(0, 4)
            .mapToObj(i -> JCraft.id("textures/entity/stands/the_hand/erase_outer" + i + ".png"))
            .toList();

    /**
     * Draws the inner and outer erasure skins for the current skin index.
     *
     * @param context AzureLib render context for this stand
     */
    @Override
    public void render(final @NonNull AzRendererPipelineContext<UUID, TheHandTheWorldEntity> context) {
        setDefaultGlow(context);
        int skin = Math.min(context.animatable().getSkin(), 3);
        RenderType cameo = RenderType.dragonExplosionAlpha(SKINS.get(skin));
        context.setRenderType(cameo);
        context.setVertexConsumer(context.multiBufferSource().getBuffer(cameo));
        context.rendererPipeline().reRender(context);
        cameo = RenderType.eyes(SKINS_OUTER.get(skin));
        context.setRenderType(cameo);
        context.setVertexConsumer(context.multiBufferSource().getBuffer(cameo));
        context.rendererPipeline().reRender(context);
    }
}
package com.example.myjaddon.client.renderer;

import com.example.myjaddon.common.stand.TheHandTheWorldEntity;
import lombok.NonNull;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import net.arna.jcraft.JCraft;
import net.arna.jcraft.client.renderer.entity.layer.AbstractRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * The Hand's erasure glow, drawn with JCraft's erase textures.
 */
@Environment(EnvType.CLIENT)
public class TheHandTheWorldErasureLayer extends AbstractRenderLayer<TheHandTheWorldEntity> {
    private static final List<ResourceLocation> SKINS = IntStream.range(0, 4)
            .mapToObj(i -> JCraft.id("textures/entity/stands/the_hand/erase" + i + ".png"))
            .toList();
    private static final List<ResourceLocation> SKINS_OUTER = IntStream.range(0, 4)
            .mapToObj(i -> JCraft.id("textures/entity/stands/the_hand/erase_outer" + i + ".png"))
            .toList();

    /**
     * Draws the inner and outer erasure skins for the current skin index.
     *
     * @param context AzureLib render context for this stand
     */
    @Override
    public void render(final @NonNull AzRendererPipelineContext<UUID, TheHandTheWorldEntity> context) {
        setDefaultGlow(context);
        int skin = Math.min(context.animatable().getSkin(), 3);
        RenderType cameo = RenderType.dragonExplosionAlpha(SKINS.get(skin));
        context.setRenderType(cameo);
        context.setVertexConsumer(context.multiBufferSource().getBuffer(cameo));
        context.rendererPipeline().reRender(context);
        cameo = RenderType.eyes(SKINS_OUTER.get(skin));
        context.setRenderType(cameo);
        context.setVertexConsumer(context.multiBufferSource().getBuffer(cameo));
        context.rendererPipeline().reRender(context);
    }
}
