package marioandweegee3.flax.client;

import marioandweegee3.flax.FlaxMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FMClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(FlaxMod.flaxCrop, RenderLayer.getCutout());
    }
}