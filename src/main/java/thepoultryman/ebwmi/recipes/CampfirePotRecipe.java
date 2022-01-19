package thepoultryman.ebwmi.recipes;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CampfirePotRecipe implements Recipe<SimpleInventory> {
    private final Ingredient ingredientA;
    private final Ingredient ingredientB;
    private final Ingredient ingredientC;
    private final int cookingTime;
    private final ItemStack outputStack;
    private final Identifier id;

    public CampfirePotRecipe(Ingredient ingredientA, Ingredient ingredientB, Ingredient ingredientC, int cookingTime, ItemStack outputStack, Identifier id) {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.ingredientC = ingredientC;
        this.cookingTime = cookingTime;
        this.outputStack = outputStack;
        this.id = id;
    }

    public Ingredient getIngredientA() {
        return ingredientA;
    }

    public Ingredient getIngredientB() {
        return ingredientB;
    }

    public Ingredient getIngredientC() {
        return ingredientC;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    private Ingredient getIngredient(int id) {
        return switch (id) {
            default -> ingredientA;
            case 1 -> ingredientB;
            case 2 -> ingredientC;
        };
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        boolean[] matches = new boolean[inventory.size()];

        if (inventory.getStack(0).isEmpty()) return false;
        else {
            for (int i = 0; i < inventory.size(); ++i) {
                matches[i] = getIngredient(i).test(inventory.getStack(i));
            }

            return isAllTrue(matches);
        }
    }

    private boolean isAllTrue(boolean[] booleanArray) {
        for (boolean b : booleanArray) if (!b) return false;
        return true;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return outputStack;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CampfirePotRecipeSerializer.INSTANCE;
    }

    public static class CampfirePotType implements RecipeType<CampfirePotRecipe> {
        private CampfirePotType() {};
        public static final CampfirePotType INSTANCE = new CampfirePotType();

        public static final String ID = "campfire_pot_recipe";
    }

    @Override
    public RecipeType<?> getType() {
        return CampfirePotType.INSTANCE;
    }
}
