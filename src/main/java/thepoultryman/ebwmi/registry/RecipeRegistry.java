package thepoultryman.ebwmi.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.ebwmi.Ebwmi;
import thepoultryman.ebwmi.recipes.CampfirePotRecipe;
import thepoultryman.ebwmi.recipes.CampfirePotRecipeSerializer;

public class RecipeRegistry {
    public static void registerCraftingRecipes() {
        registerType();
        registerRecipeSerializer();
    }

    private static void registerType() {
        Registry.register(Registry.RECIPE_TYPE, new Identifier(Ebwmi.MOD_ID, CampfirePotRecipe.CampfirePotType.ID), CampfirePotRecipe.CampfirePotType.INSTANCE);
    }

    private static void registerRecipeSerializer() {
        Registry.register(Registry.RECIPE_SERIALIZER, CampfirePotRecipeSerializer.ID, CampfirePotRecipeSerializer.INSTANCE);
    }
}
