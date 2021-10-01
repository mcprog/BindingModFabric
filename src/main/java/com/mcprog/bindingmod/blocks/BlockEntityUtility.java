package com.mcprog.bindingmod.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class BlockEntityUtility {

    public static boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe, ItemStack input, ItemStack output, int count) {
        if (!input.isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.getOutput();
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = output;
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!itemStack2.isItemEqualIgnoreDamage(itemStack)) {
                    return false;
                } else if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
                    return true;
                } else {
                    return itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }
}
