package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.blocks.StorageCrateBE;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.slot.Slot;

public class StorageCrateScreenHandler extends SimpleScreenHandler implements MachineScreenHandler{
    public StorageCrateScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(StorageCrateBE.INV_SIZE));
    }

    public StorageCrateScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(Registration.STORAGE_CRATE_SCREEN_HANDLER, syncId, playerInventory, inventory, StorageCrateBE.INV_SIZE);
        setupMachineSlots();
    }

    @Override
    public void setupMachineSlots() {
        int index = 0;
        for (int grid = 0; grid < 3; grid++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    this.addSlot(new Slot(this.inventory, index, grid * 58 + 8 + j * 18, 18 + i * 18));
                    ++index;
                }
            }
        }
    }

    @Override
    protected void setupPlayerSlots(PlayerInventory playerInventory) {
        int m;
        int l;

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 12 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 12 + m * 18, 142));
        }
    }
}
