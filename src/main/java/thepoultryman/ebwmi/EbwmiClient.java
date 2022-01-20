package thepoultryman.ebwmi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import thepoultryman.ebwmi.blockentityrenderers.CampfirePotBlockEntityRenderer;
import thepoultryman.ebwmi.registry.BlockEntityRegistry;
import thepoultryman.ebwmi.registry.BlockRegistry;
import thepoultryman.ebwmi.registry.BlockRendererRegistry;

public class EbwmiClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRendererRegistry.registerBlockEntityRenderers();
	}
}
