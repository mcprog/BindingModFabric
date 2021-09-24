package com.mcprog.bindingmod.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface SimpleInventory extends Inventory {

    DefaultedList<ItemStack> getItems();

    static SimpleInventory of(DefaultedList<ItemStack> items) {
        return () -> items;
    }

    /*
     * Does what of() does, but with specified size
     */
    static SimpleInventory ofSize(int size) {
        return of(DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    @Override
    default int size() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < size(); ++i) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    default ItemStack getStack(int slot) {
        return getItems().get(slot);
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(getItems(), slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getItems(), slot);
    }

    /**
     * If stack is too big for this inventory it gets resized to this inventory's max amount.
     * @param slot which slot to replace the itemstack of
     * @param stack the itemstack that will replace the current itemstack
     */
    @Override
    default void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        int max = getMaxCountPerStack();
        if (stack.getCount() > max) {
            stack.setCount(max);
        }
    }

    @Override
    default void clear() {
        getItems().clear();
    }

    @Override
    default void markDirty() {
        // Override if you want behavior.
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}
