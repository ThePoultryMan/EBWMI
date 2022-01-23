package thepoultryman.ebwmi.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import thepoultryman.ebwmi.DamageSources;

import java.util.List;

public class MaliciousKazoo extends Item {
    public MaliciousKazoo(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            Box box = user.getBoundingBox().expand(4D, 2D, 4D);
            List<LivingEntity> nonSpectatingEntities = world.getNonSpectatingEntities(LivingEntity.class, box);
            for (LivingEntity nonSpectatingEntity : nonSpectatingEntities) {
                if (!nonSpectatingEntity.equals(user)) {
                    nonSpectatingEntity.damage(DamageSources.SOUND, 2f);
                }
            }
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
