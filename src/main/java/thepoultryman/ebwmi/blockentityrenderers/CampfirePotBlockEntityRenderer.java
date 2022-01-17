package thepoultryman.ebwmi.blockentityrenderers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3f;
import thepoultryman.ebwmi.blockentities.CampfirePotEntity;

import java.util.Random;

public class CampfirePotBlockEntityRenderer implements BlockEntityRenderer<CampfirePotEntity> {
    private static final int[] ROTATION = {new Random().nextInt(360), new Random().nextInt(360), new Random().nextInt(360)};

    public CampfirePotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(CampfirePotEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        DefaultedList<ItemStack> ingredients = entity.getIngredients();

        for (int i = 0; i < ingredients.size(); ++i) {
            matrices.push();
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.scale(0.325f, 0.325f, 0.325f);
            matrices.translate(0.0d, 1.2d + (0.06d * i), 0d);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90f));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(ROTATION[i]));
            MinecraftClient.getInstance().getItemRenderer().renderItem(ingredients.get(i), ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, (int) entity.getPos().asLong());
            matrices.pop();
        }
    }
}
