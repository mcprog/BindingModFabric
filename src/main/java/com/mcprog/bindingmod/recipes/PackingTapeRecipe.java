package com.mcprog.bindingmod.recipes;

import com.mcprog.bindingmod.items.PackingTapeItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class PackingTapeRecipe extends ShapelessRecipe {
    public PackingTapeRecipe(ShapelessRecipe original) {
        super(original.getId(), original.getGroup(), original.getOutput(), original.getIngredients());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PackingTapeRecipeSerializer.INSTANCE;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> items = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            Item item = stack.getItem();

            if (item instanceof PackingTapeItem) {
                int newDamage = stack.getDamage() + 1;
                if (newDamage < stack.getMaxDamage()) {
                    stack = stack.copy();
                    stack.setDamage(newDamage);
                    items.set(i, stack);
                }
            }
            else if (item.hasRecipeRemainder()) {
                items.set(i, new ItemStack(item.getRecipeRemainder()));
            }
            //else it is ItemStack.EMPTY
        }
        return items;
    }
}
