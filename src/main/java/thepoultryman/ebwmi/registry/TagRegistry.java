package thepoultryman.ebwmi.registry;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import thepoultryman.ebwmi.Ebwmi;

public class TagRegistry {
    // General Tags
    public static Tag<Item> UNCOOKABLE;
    public static Tag<Item> BREAD;

    // Extractor Tags
    public static Tag<EntityType<?>> EXTRACTOR_MALICIOUS_INTENT;
    public static Tag<EntityType<?>> EXTRACTOR_GOOD_INTENT;
    public static Tag<EntityType<?>> EXTRACTOR_NEUTRAL_INTENT;

    public static void registerTags() {
        UNCOOKABLE = registerCommonItemTag("ebwmi_uncookable");
        BREAD = registerCommonItemTag("ebwmi_bread");
        EXTRACTOR_MALICIOUS_INTENT = registerCommonEntityTag("extractor_malicious");
        EXTRACTOR_GOOD_INTENT = registerCommonEntityTag("extractor_good");
        EXTRACTOR_NEUTRAL_INTENT = registerCommonEntityTag("extractor_neutral");
    }

    private static Tag<Item> registerItemTag(String name) {
        return TagFactory.ITEM.create(new Identifier(Ebwmi.MOD_ID, name));
    }

    private static Tag<Item> registerCommonItemTag(String name) {
        return TagFactory.ITEM.create(new Identifier("c", name));
    }

    private static Tag<EntityType<?>> registerCommonEntityTag(String name) {
        return TagFactory.ENTITY_TYPE.create(new Identifier("c", name));
    }
}
