package thepoultryman.ebwmi.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import thepoultryman.ebwmi.blockentityrenderer.CampfirePotBlockEntityRenderer;

public class BlockRendererRegistry {
    private static final Block[] TRANSPARENT_TEXTURE_BLOCKS = {BlockRegistry.CAMPFIRE_POT};

    public static void registerBlockEntityRenderers() {
        register();
    }

    private static void register() {
        BlockEntityRendererRegistry.register(BlockEntityRegistry.CAMPFIRE_POT_BLOCK_ENTITY, CampfirePotBlockEntityRenderer::new);

        for (Block transparentTextureBlock : TRANSPARENT_TEXTURE_BLOCKS) {
            BlockRenderLayerMap.INSTANCE.putBlock(transparentTextureBlock, RenderLayer.getCutout());
        }
    }
}
