package thepoultryman.ebwmi;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thepoultryman.ebwmi.registry.*;

public class Ebwmi implements ModInitializer {
	public static final String MOD_ID = "ebwmi";

	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Don't mind me, just initializing bread with malicious intent.");

		BlockRegistry.registerBlocks();
		BlockEntityRegistry.registerBlockEntities();
		ItemRegistry.registerItems();
		TagRegistry.registerTags();
		StatusEffectRegistry.registerStatusEffects();
		RecipeRegistry.registerCraftingRecipes();
	}
}
