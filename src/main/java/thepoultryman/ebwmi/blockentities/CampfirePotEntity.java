package thepoultryman.ebwmi.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import thepoultryman.ebwmi.Ebwmi;
import thepoultryman.ebwmi.blocks.CampfirePot;
import thepoultryman.ebwmi.recipes.CampfirePotRecipe;
import thepoultryman.ebwmi.registry.BlockEntityRegistry;

import java.util.Optional;

public class CampfirePotEntity extends BlockEntity{
    public final DefaultedList<ItemStack> ingredients;
    private SimpleInventory cookingInventory = new SimpleInventory(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
    private int cookingTime;

    public CampfirePotEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.CAMPFIRE_POT_BLOCK_ENTITY, blockPos, blockState);
        ingredients = DefaultedList.ofSize(3, ItemStack.EMPTY);
    }

    public void addIngredient(ItemStack ingredient) {
        if (ingredient.isEmpty() || ingredient.isIn(Ebwmi.UNCOOKABLE)) return;
        for (int i = 0; i < ingredients.size(); ++i) {
            ItemStack currentIngredient = (ItemStack) ingredients.get(i);
            if (currentIngredient.isEmpty()) {
                ingredients.set(i, ingredient.split(1));
                updateListeners();
                return;
            }
        }
    }

    public void removeIngredients(World world, BlockPos pos) {
        for (ItemStack ingredient : ingredients) {
            if (ingredient.isEmpty()) return;
            ItemScatterer.spawn(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), ingredient);
            updateListeners();
        }
    }

    public void useIngredients() {
        cookingInventory = new SimpleInventory(ingredients.get(0), ingredients.get(1), ingredients.get(2));
        updateListeners();
    }

    public boolean isIngredientBlockItem(ItemStack ingredient) {
        return ingredient.getItem() instanceof BlockItem;
    }

    public DefaultedList<ItemStack> getIngredients() {
        return ingredients;
    }

    public void cookingTick(World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (!world.isClient && blockState.get(CampfirePot.LIT)) {
            if (cookingInventory.isEmpty()) cookingInventory = new SimpleInventory(ingredients.get(0), ingredients.get(1), ingredients.get(2));

            Optional<CampfirePotRecipe> match = world.getRecipeManager().getFirstMatch(CampfirePotRecipe.CampfirePotType.INSTANCE, cookingInventory, world);

            if (match.isPresent() && cookingTime != 0) {
                --cookingTime;
                markDirty(world, pos, blockState);
            } else if (match.isPresent() && cookingTime < (match.get().getCookingTime())) {
                extinguishFire(world, blockState);
                clear();
                ItemScatterer.spawn(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), match.get().getOutput().copy());
                updateListeners();
            }
        }
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    private void extinguishFire(World world, BlockState blockState) {
        world.setBlockState(pos, blockState.with(CampfirePot.LIT, false));
        cookingTime = 0;
    }

    private void clear() {
        ingredients.clear();
        cookingInventory.clear();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        ingredients.clear();
        Inventories.readNbt(nbt, ingredients);
        if (nbt.contains("CookingTime")) {cookingTime = nbt.getInt("CookingTime");}
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, ingredients, true);
        nbt.putInt("CookingTime", cookingTime);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.of(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, ingredients, true);
        return nbtCompound;
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }
}
