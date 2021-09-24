package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.blocks.IntegrationGeneratorBE;
import com.mcprog.bindingmod.blocks.IntegrationGeneratorBlock;
import com.mcprog.bindingmod.recipes.IntegrationRecipe;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class IntegrationGeneratorScreenHandler extends SimpleScreenHandler {

    public IntegrationGeneratorScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory);
    }

    public IntegrationGeneratorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, inventory, context);
    }

    @Override
    protected void setupSlots() {
        this.addSlot(new Slot(inventory, 0, 32, 34));
        this.addSlot(new Slot(inventory, 1, 129, 34));
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        if (slot.id == 1) {
            return false;
        }
        return true;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        System.out.printf("onContentChanged called.\n");
        /*context.run((world, blockPos) -> {
            if (!world.isClient) {
                Optional<IntegrationRecipe> matches = world.getRecipeManager().getFirstMatch(IntegrationRecipe.Type.INSTANCE, (CraftingInventory) inventory, world);
                System.out.printf("onContentChanged called.\n");
                if (matches.isPresent()) {
                    System.out.printf("Found recipe match for integration generator\n");
                    IntegrationRecipe recipe = matches.get();
                    ItemStack result = recipe.craft((CraftingInventory) inventory);
                    inventory.setStack(IntegrationGeneratorBlock.OUTPUT_SLOT, result);
                }
            }
        });*/
    }
}
