package thepoultryman.ebwmi.registry;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import thepoultryman.ebwmi.Ebwmi;

public class TagRegistry {
    // Tags
    public static Tag<Item> UNCOOKABLE;
    public static Tag<Item> BREAD;

    public static void registerTags() {
        UNCOOKABLE = registerCommonItemTag("ebwmi_uncookable");
        BREAD = registerCommonItemTag("ebwmi_bread");
    }

    private static Tag<Item> registerItemTag(String name) {
        return TagFactory.ITEM.create(new Identifier(Ebwmi.MOD_ID, name));
    }

    private static Tag<Item> registerCommonItemTag(String name) {
        return TagFactory.ITEM.create(new Identifier("c", name));
    }
}
