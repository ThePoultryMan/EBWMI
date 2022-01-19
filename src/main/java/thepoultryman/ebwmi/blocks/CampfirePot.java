package thepoultryman.ebwmi.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import thepoultryman.ebwmi.Ebwmi;
import thepoultryman.ebwmi.blockentities.CampfirePotEntity;
import thepoultryman.ebwmi.recipes.CampfirePotRecipe;

import java.util.Optional;

public class CampfirePot extends BlockWithEntity {
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE;
    private static final VoxelShape SHAPE_TURNED;

    public CampfirePot(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false).with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stackInHand = player.getStackInHand(hand);

        if (!world.isClient && world.getBlockEntity(pos) instanceof CampfirePotEntity campfirePotEntity && !campfirePotEntity.isIngredientBlockItem(stackInHand) && !state.get(LIT)) {
            if (!player.isSneaking()) {
                if (stackInHand.getItem().equals(Items.FLINT_AND_STEEL)) {
                   setLit(world, pos, state, campfirePotEntity);
                } else {
                    campfirePotEntity.addIngredient(stackInHand);
                }
            } else {
                campfirePotEntity.removeIngredients(world, pos);
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case EAST, WEST -> SHAPE_TURNED;
            default -> SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(LIT, false).with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, FACING);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CampfirePotEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Ebwmi.CAMPFIRE_POT_BLOCK_ENTITY_TYPE, (world1, pos, state1, blockEntity) -> blockEntity.cookingTick(world1, pos));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    private static void setLit(World world, BlockPos pos, BlockState state, CampfirePotEntity campfirePotEntity) {
        world.setBlockState(pos, state.with(LIT, true));
        DefaultedList<ItemStack> ingredients = campfirePotEntity.getIngredients();
        SimpleInventory inventory = new SimpleInventory(ingredients.get(0), ingredients.get(1), ingredients.get(2));
        Optional<CampfirePotRecipe> match = world.getRecipeManager().getFirstMatch(CampfirePotRecipe.CampfirePotType.INSTANCE, inventory, world);

        if (match.isPresent()) {
            campfirePotEntity.useIngredients();
            campfirePotEntity.setCookingTime(match.get().getCookingTime());
        }
    }

    static {
        final VoxelShape base = Block.createCuboidShape(0d, 0d, 0d, 16d, 7d, 16d);

        final VoxelShape pot = VoxelShapes.combineAndSimplify(Block.createCuboidShape(4d, 13d, 4d, 12d, 19d, 12d), Block.createCuboidShape(5d, 14d, 5d, 11d, 19d, 11d), BooleanBiFunction.ONLY_FIRST);

        final VoxelShape poles1 = VoxelShapes.combineAndSimplify(Block.createCuboidShape(0d, 0d, 7d, 1d, 22d, 9d), Block.createCuboidShape(15d, 0d, 7d, 16d, 22d, 9d), BooleanBiFunction.OR);
        final VoxelShape poles2 = VoxelShapes.combineAndSimplify(Block.createCuboidShape(7d, 0d, 0d, 9d, 22d, 1d), Block.createCuboidShape(7d, 0d, 15d, 9d, 22, 16d), BooleanBiFunction.OR);

        final VoxelShape handles1 = VoxelShapes.combineAndSimplify(Block.createCuboidShape(3d, 17d, 6.5d, 4d, 18d, 9.5d), Block.createCuboidShape(12d, 17, 6.5d, 13d, 18d, 9.5d), BooleanBiFunction.OR);
        final VoxelShape handles2 = VoxelShapes.combineAndSimplify(Block.createCuboidShape(6.5d, 17d, 3d, 9.5d, 18d, 4d), Block.createCuboidShape(6.5d, 17d, 12d, 9.5d, 18d, 13d), BooleanBiFunction.OR);

        final VoxelShape polesAndHandles1 = VoxelShapes.combineAndSimplify(poles1, handles1, BooleanBiFunction.OR);
        final VoxelShape polesAndHandles2 = VoxelShapes.combineAndSimplify(poles2, handles2, BooleanBiFunction.OR);

        final VoxelShape baseAndPot = VoxelShapes.combineAndSimplify(base, pot, BooleanBiFunction.OR);
        SHAPE = VoxelShapes.combineAndSimplify(baseAndPot, polesAndHandles1, BooleanBiFunction.OR);
        SHAPE_TURNED = VoxelShapes.combineAndSimplify(baseAndPot, polesAndHandles2, BooleanBiFunction.OR);
    }
}
