package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.inventory.SimpleInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class InventoryBE extends BlockEntity implements SimpleInventory {
    public InventoryBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, getItems());
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, getItems());
        return super.writeNbt(nbt);
    }

    protected static void transferStackIn(Inventory blockInventory, int slot, ItemStack stackToTransfer) {
        blockInventory.setStack(slot,stackToTransfer.copy());
        stackToTransfer.setCount(0);
    }

    protected static void transferStackOut(Inventory blockInventory, PlayerInventory playerInventory, int slot) {
        playerInventory.offerOrDrop(blockInventory.getStack(slot));
        blockInventory.removeStack(slot);
    }
}
