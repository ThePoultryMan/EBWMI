package thepoultryman.ebwmi.registry;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.ebwmi.Ebwmi;
import thepoultryman.ebwmi.items.MaliciousBread;
import thepoultryman.ebwmi.items.MaliciousKazoo;

public class ItemRegistry {
    public static final Item MALICIOUS_BREAD = new MaliciousBread(new Item.Settings().group(ItemGroup.FOOD).maxCount(1).maxDamage(64).food(new FoodComponent.Builder().hunger(1).alwaysEdible().build()));
    public static final Item MALICIOUS_KAZOO = new MaliciousKazoo(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(32));
    public static final Item POT = new Item(new Item.Settings().group(ItemGroup.MISC));

    // Purified Intent
    public static final Item PURIFIED_MALICIOUS_INTENT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item PURIFIED_GOOD_INTENT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item PURIFIED_NEUTRAL_INTENT = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static void registerItems() {
        register("malicious_bread", MALICIOUS_BREAD);
        register("malicious_kazoo", MALICIOUS_KAZOO);
        register("purified_malicious_intent", PURIFIED_MALICIOUS_INTENT);
        register("purified_good_intent", PURIFIED_GOOD_INTENT);
        register("purified_neutral_intent", PURIFIED_NEUTRAL_INTENT);
        register("pot", POT);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Ebwmi.MOD_ID, name), item);
    }
}
