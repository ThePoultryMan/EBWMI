package thepoultryman.ebwmi.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thepoultryman.ebwmi.registry.ItemRegistry;
import thepoultryman.ebwmi.registry.StatusEffectRegistry;

public class MaliciousBread extends Item {
    public MaliciousBread(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.getStackInHand(livingEntity.getActiveHand()).getItem() == ItemRegistry.MALICIOUS_BREAD)
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.MALICIOUS_INTENT_STATUS_EFFECT, 220));
        else if (entity instanceof LivingEntity livingEntity)
            livingEntity.removeStatusEffect(StatusEffectRegistry.MALICIOUS_INTENT_STATUS_EFFECT);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        stack.damage(1, user, (userx) -> {
            userx.sendToolBreakStatus(user.preferredHand);
        });

        user.eatFood(world, new ItemStack(ItemRegistry.MALICIOUS_BREAD));

        return stack;
    }
}
