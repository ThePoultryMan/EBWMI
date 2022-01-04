package thepoultryman.ebwmi;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MaliciousBread extends Item {
	public MaliciousBread(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		stack.damage(1, user, (userx) -> {
			userx.sendToolBreakStatus(user.preferredHand);
		});

		return stack;
	}
}
