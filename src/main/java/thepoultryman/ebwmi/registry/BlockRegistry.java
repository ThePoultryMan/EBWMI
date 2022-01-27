package thepoultryman.ebwmi.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.ebwmi.Ebwmi;
import thepoultryman.ebwmi.block.CampfirePot;

import java.util.function.ToIntFunction;

public class BlockRegistry {
    // Blocks
    public static final Block CAMPFIRE_POT = new CampfirePot(FabricBlockSettings.of(Material.STONE).luminance(getLitBlockStateLightLevel(15)).nonOpaque());

    public static void registerBlocks() {
        register("campfire_pot", CAMPFIRE_POT, ItemGroup.DECORATIONS);
    }

    private static void register(String name, Block block, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(Ebwmi.MOD_ID, name), block);
        Registry.register(Registry.ITEM, new Identifier(Ebwmi.MOD_ID, name), new BlockItem(block, new Item.Settings().group(itemGroup)));
    }

    private static ToIntFunction<BlockState> getLitBlockStateLightLevel(int lightLevel) {
        return (state) -> {
            return state.get(Properties.LIT) ? lightLevel : 0;
        };
    }
}
