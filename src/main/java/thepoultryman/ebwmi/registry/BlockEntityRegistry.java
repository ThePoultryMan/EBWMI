package thepoultryman.ebwmi.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import thepoultryman.ebwmi.blockentities.CampfirePotEntity;

public class BlockEntityRegistry {
    public static final BlockEntityType<CampfirePotEntity> CAMPFIRE_POT_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(((blockPos, blockState) -> new CampfirePotEntity(blockPos, blockState)), BlockRegistry.CAMPFIRE_POT).build();

    public static void registerBlockEntities() {
        register("campfire_pot_entity", CAMPFIRE_POT_BLOCK_ENTITY);
    }

    private static void register(String id, BlockEntityType<?> blockEntityType) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, "ebwmi:" + id, blockEntityType);
    }
}
