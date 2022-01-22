package thepoultryman.ebwmi;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thepoultryman.ebwmi.recipes.CampfirePotRecipe;
import thepoultryman.ebwmi.recipes.CampfirePotRecipeSerializer;
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



		// Recipe Stuff
		Registry.register(Registry.RECIPE_SERIALIZER, CampfirePotRecipeSerializer.ID, CampfirePotRecipeSerializer.INSTANCE);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, CampfirePotRecipe.CampfirePotType.ID), CampfirePotRecipe.CampfirePotType.INSTANCE);
	}
}
