package thepoultryman.ebwmi.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import thepoultryman.ebwmi.Ebwmi;
import thepoultryman.ebwmi.registry.ItemRegistry;
import thepoultryman.ebwmi.registry.TagRegistry;

public class Extractor extends Item {
    public Extractor(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient) {
            EntityType<?> entityType = entity.getType();

            if (entityType.isIn(TagRegistry.EXTRACTOR_MALICIOUS_INTENT)) {
                user.setStackInHand(hand, new ItemStack(ItemRegistry.EXTRACTOR_MALICIOUS));
                return ActionResult.SUCCESS;
            } else if (entityType.isIn(TagRegistry.EXTRACTOR_GOOD_INTENT)) {
                user.setStackInHand(hand, new ItemStack(ItemRegistry.EXTRACTOR_GOOD));
                return ActionResult.SUCCESS;
            } else if (entityType.isIn(TagRegistry.EXTRACTOR_NEUTRAL_INTENT)) {
                user.setStackInHand(hand, new ItemStack(ItemRegistry.EXTRACTOR_NEUTRAL));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }

        } else {
            return ActionResult.PASS;
        }
    }
}
