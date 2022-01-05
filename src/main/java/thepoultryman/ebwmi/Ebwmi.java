package thepoultryman.ebwmi;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ebwmi implements ModInitializer {
	public static final String MOD_ID = "ebwmi";

	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	// Items
	public static final Item MALICIOUS_BREAD = new MaliciousBread(new Item.Settings().group(ItemGroup.FOOD).maxCount(1).maxDamage(64).food(new FoodComponent.Builder().alwaysEdible().build()));

	// Status Effects
	public static final StatusEffect MALICIOUS_INTENT_STATUS_EFFECT = new MaliciousIntentStatusEffect();

	@Override
	public void onInitialize() {
		LOGGER.info("Don't mind me, just initializing bread with malicious intent.");

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "malicious_bread"), MALICIOUS_BREAD);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "malicious_intent"), MALICIOUS_INTENT_STATUS_EFFECT);

	}
}
