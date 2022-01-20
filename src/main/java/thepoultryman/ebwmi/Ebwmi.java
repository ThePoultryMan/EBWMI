package thepoultryman.ebwmi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thepoultryman.ebwmi.blockentities.CampfirePotEntity;
import thepoultryman.ebwmi.blocks.CampfirePot;
import thepoultryman.ebwmi.items.MaliciousBread;
import thepoultryman.ebwmi.recipes.CampfirePotRecipe;
import thepoultryman.ebwmi.recipes.CampfirePotRecipeSerializer;
import thepoultryman.ebwmi.statuseffects.MaliciousIntentStatusEffect;

import java.util.function.ToIntFunction;

public class Ebwmi implements ModInitializer {
	public static final String MOD_ID = "ebwmi";

	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	private static ToIntFunction<BlockState> getLitBlockStateLightLevel(int lightLevel) {
		return (state) -> {
			return state.get(Properties.LIT) ? lightLevel : 0;
		};
	}

	// Blocks
	public static final Block CAMPFIRE_POT = new CampfirePot(FabricBlockSettings.of(Material.STONE).luminance(getLitBlockStateLightLevel(15)).nonOpaque());

	// Block Entities
	public static BlockEntityType<CampfirePotEntity> CAMPFIRE_POT_BLOCK_ENTITY_TYPE;

	// Items
	public static final Item MALICIOUS_BREAD = new MaliciousBread(new Item.Settings().group(ItemGroup.FOOD).maxCount(1).maxDamage(64).food(new FoodComponent.Builder().hunger(1).alwaysEdible().build()));

	// Status Effects
	public static final StatusEffect MALICIOUS_INTENT_STATUS_EFFECT = new MaliciousIntentStatusEffect();

	// Tags
	public static Tag<Item> EBWMI_UNCOOKABLE;
	public static Tag<Item> BREAD;

	@Override
	public void onInitialize() {
		LOGGER.info("Don't mind me, just initializing bread with malicious intent.");

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "malicious_bread"), MALICIOUS_BREAD);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "malicious_intent"), MALICIOUS_INTENT_STATUS_EFFECT);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "campfire_pot"), CAMPFIRE_POT);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "campfire_pot"), new BlockItem(CAMPFIRE_POT, new Item.Settings().group(ItemGroup.DECORATIONS)));
		CAMPFIRE_POT_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "ebwmi:oven_block_entity", FabricBlockEntityTypeBuilder.create((blockPos, blockState) -> new CampfirePotEntity(blockPos, blockState), CAMPFIRE_POT).build(null));

		EBWMI_UNCOOKABLE = TagFactory.ITEM.create(new Identifier("c", "ebwmi_uncookable"));
		BREAD = TagFactory.ITEM.create(new Identifier("c", "ebwmi_bread"));

		// Recipe Stuff
		Registry.register(Registry.RECIPE_SERIALIZER, CampfirePotRecipeSerializer.ID, CampfirePotRecipeSerializer.INSTANCE);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, CampfirePotRecipe.CampfirePotType.ID), CampfirePotRecipe.CampfirePotType.INSTANCE);
	}
}
