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
    public static final Item MALICIOUS_KAZOO = new MaliciousKazoo(new Item.Settings().group(ItemGroup.COMBAT).maxCount(1));

    public static void registerItems() {
        register("malicious_bread", MALICIOUS_BREAD);
        register("malicious_kazoo", MALICIOUS_KAZOO);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Ebwmi.MOD_ID, name), item);
    }
}
