package thepoultryman.ebwmi.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CampfirePotRecipeSerializer implements RecipeSerializer<CampfirePotRecipe> {
    private CampfirePotRecipeSerializer() {
    }

    public static final CampfirePotRecipeSerializer INSTANCE = new CampfirePotRecipeSerializer();
    public static final Identifier ID = new Identifier("ebwmi:campfire_pot_recipe");

    @Override
    public CampfirePotRecipe read(Identifier id, JsonObject json) {
        CampfirePotRecipeJsonFormat recipeJson = new Gson().fromJson(json, CampfirePotRecipeJsonFormat.class);

        Ingredient ingredientA;
        Ingredient ingredientB;
        Ingredient ingredientC;

        if (recipeJson.ingredientA == null || recipeJson.outputItem == null) {
            throw new JsonSyntaxException("(EBWMI) A required attribute is missing!");
        }
        if (recipeJson.cookingTime == 0) recipeJson.cookingTime = 20;
        if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;

        ingredientA = Ingredient.fromJson(recipeJson.ingredientA);
        if (recipeJson.ingredientB == null) {
            ingredientB = Ingredient.EMPTY;
            ingredientC = Ingredient.EMPTY;
        } else if (recipeJson.ingredientC == null) {
            ingredientB = Ingredient.fromJson(recipeJson.ingredientB);
            ingredientC = Ingredient.EMPTY;
        } else {
            ingredientB = Ingredient.fromJson(recipeJson.ingredientB);
            ingredientC = Ingredient.fromJson(recipeJson.ingredientC);
        }

        int cookingTime = recipeJson.cookingTime * 5;
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).orElseThrow(() -> new JsonSyntaxException("(EBWMI) No such item: " + recipeJson.outputItem));
        ItemStack outputStack = new ItemStack(outputItem, recipeJson.outputAmount);

        return new CampfirePotRecipe(ingredientA, ingredientB, ingredientC, cookingTime,outputStack, id);
    }

    @Override
    public void write(PacketByteBuf buf, CampfirePotRecipe recipe) {
        recipe.getIngredientA().write(buf);
        recipe.getIngredientB().write(buf);
        recipe.getIngredientC().write(buf);
        buf.writeInt(recipe.getCookingTime());
        buf.writeItemStack(recipe.getOutput());
    }

    @Override
    public CampfirePotRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient ingredientA = Ingredient.fromPacket(buf);
        Ingredient ingredientB = Ingredient.fromPacket(buf);
        Ingredient ingredientC = Ingredient.fromPacket(buf);
        int cookingTime = buf.readInt();
        ItemStack outputStack = buf.readItemStack();
        return new CampfirePotRecipe(ingredientA, ingredientB, ingredientC, cookingTime, outputStack, id);
    }
}
