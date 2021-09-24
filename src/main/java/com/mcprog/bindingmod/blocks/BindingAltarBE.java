package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.inventory.SimpleInventory;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class BindingAltarBE extends InventoryBE {

    public static int INV_SIZE = 4;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);

    public BindingAltarBE(BlockPos pos, BlockState state) {
        super(Registration.BINDING_ALTAR_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
}
