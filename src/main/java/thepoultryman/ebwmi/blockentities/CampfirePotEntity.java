package thepoultryman.ebwmi.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
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

public class CampfirePotEntity extends BlockEntity{
	public final DefaultedList<ItemStack> ingredients;

	public CampfirePotEntity(BlockPos blockPos, BlockState blockState) {
		super(Ebwmi.CAMPFIRE_POT_BLOCK_ENTITY_TYPE, blockPos, blockState);
		ingredients = DefaultedList.ofSize(3, ItemStack.EMPTY);
	}

	public void addIngredient(ItemStack ingredient) {
		if (ingredient.isEmpty() || ingredient.isIn(Ebwmi.EBWMI_UNCOOKABLE)) return;
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
		for (int i = 0; i < ingredients.size(); ++i) {
			if (ingredients.get(i).isEmpty()) return;
			ItemScatterer.spawn(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), ingredients.get(i));
			updateListeners();
		}
	}

	public void useIngredients() {
		ingredients.clear();
		updateListeners();
	}

	public boolean isIngredientBlockItem(ItemStack ingredient) {
		return ingredient.getItem() instanceof BlockItem;
	}

	public DefaultedList<ItemStack> getIngredients() {
		return ingredients;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		ingredients.clear();
		Inventories.readNbt(nbt, ingredients);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, ingredients, true);
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
