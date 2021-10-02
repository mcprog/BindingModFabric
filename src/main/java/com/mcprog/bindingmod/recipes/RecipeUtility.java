package com.mcprog.bindingmod.recipes;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public class RecipeUtility {

    public static RecipeType getTypeInstance(Class<? extends Recipe> recipeClass) {
        if (recipeClass.equals(IntegrationRecipe.class)) {
            return IntegrationRecipe.Type.INSTANCE;
        }
        return null;
    }
}
