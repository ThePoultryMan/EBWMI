package thepoultryman.ebwmi;

import net.fabricmc.api.ClientModInitializer;
import thepoultryman.ebwmi.registry.BlockRendererRegistry;

public class EbwmiClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRendererRegistry.registerBlockEntityRenderers();
	}
}
