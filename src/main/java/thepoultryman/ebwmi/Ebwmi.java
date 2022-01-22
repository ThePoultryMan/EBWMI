package thepoultryman.ebwmi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thepoultryman.ebwmi.items.MaliciousBread;
import thepoultryman.ebwmi.recipes.CampfirePotRecipe;
import thepoultryman.ebwmi.recipes.CampfirePotRecipeSerializer;
import thepoultryman.ebwmi.registry.BlockEntityRegistry;
import thepoultryman.ebwmi.registry.BlockRegistry;
import thepoultryman.ebwmi.registry.ItemRegistry;
import thepoultryman.ebwmi.statuseffects.MaliciousIntentStatusEffect;

public class Ebwmi implements ModInitializer {
	public static final String MOD_ID = "ebwmi";

	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	// Status Effects
	public static final StatusEffect MALICIOUS_INTENT_STATUS_EFFECT = new MaliciousIntentStatusEffect();

	// Tags
	public static Tag<Item> UNCOOKABLE;
	public static Tag<Item> BREAD;

	@Override
	public void onInitialize() {
		LOGGER.info("Don't mind me, just initializing bread with malicious intent.");

		BlockRegistry.registerBlocks();
		BlockEntityRegistry.registerBlockEntities();
		ItemRegistry.registerItems();

		Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "malicious_intent"), MALICIOUS_INTENT_STATUS_EFFECT);

		UNCOOKABLE = TagFactory.ITEM.create(new Identifier("c", "ebwmi_uncookable"));
		BREAD = TagFactory.ITEM.create(new Identifier("c", "ebwmi_bread"));

		// Recipe Stuff
		Registry.register(Registry.RECIPE_SERIALIZER, CampfirePotRecipeSerializer.ID, CampfirePotRecipeSerializer.INSTANCE);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, CampfirePotRecipe.CampfirePotType.ID), CampfirePotRecipe.CampfirePotType.INSTANCE);
	}
}
