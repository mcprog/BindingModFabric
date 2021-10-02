package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.blocks.IntegrationGeneratorBE;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class SimpleScreenHandler extends ScreenHandler{

    protected final Inventory inventory;

    public SimpleScreenHandler(ScreenHandlerType type, int syncId, PlayerInventory playerInventory, int expectedInvSize) {
        this(type, syncId, playerInventory, new SimpleInventory(expectedInvSize), expectedInvSize);
    }

    public SimpleScreenHandler(ScreenHandlerType type,int syncId, PlayerInventory playerInventory, Inventory inventory, int expectedInvSize) {
        super(type, syncId);
        checkSize(inventory, expectedInvSize);
        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);
        setupPlayerSlots(playerInventory);
    }

    protected void setupPlayerSlots(PlayerInventory playerInventory) {
        int m;
        int l;

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
        
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

}
