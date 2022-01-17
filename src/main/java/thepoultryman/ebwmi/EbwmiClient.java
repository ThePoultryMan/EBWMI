package thepoultryman.ebwmi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import thepoultryman.ebwmi.blockentityrenderers.CampfirePotBlockEntityRenderer;

public class EbwmiClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(Ebwmi.CAMPFIRE_POT_BLOCK_ENTITY_TYPE, CampfirePotBlockEntityRenderer::new);

		// Transparent Textures
		BlockRenderLayerMap.INSTANCE.putBlock(Ebwmi.CAMPFIRE_POT, RenderLayer.getCutout());
	}
}
