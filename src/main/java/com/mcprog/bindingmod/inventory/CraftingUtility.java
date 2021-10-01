package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.blocks.BlockEntityUtility;
import com.mcprog.bindingmod.recipes.IntegrationRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CraftingUtility {

    public static void updateCraftingResult(ScreenHandler handler, World world, PlayerEntity player, RecipeType recipeType, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {
            System.out.println("Update crafting result called");
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<IntegrationRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(recipeType, craftingInventory, world);

            System.out.println(optional.toString());
            if (optional.isPresent()) {
                System.out.println("Result is present");
                IntegrationRecipe integrationRecipe = (IntegrationRecipe) optional.get();
                itemStack = integrationRecipe.craft(craftingInventory);
            }

            resultInventory.setStack(0, itemStack);
            handler.setPreviousTrackedSlot(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
        }
    }

    /*
     * Note: only works when add to stack is +1 for output slot
     */
    public static boolean craftRecipe(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int inputIndex, int outputIndex, int count) {
        if (recipe == null) {
            return false;
        }

        ItemStack inputStack = slots.get(inputIndex);
        ItemStack outputStack = slots.get(outputIndex);

        if (BlockEntityUtility.canAcceptRecipeOutput(recipe, inputStack, outputStack, count)) {
            ItemStack expectedOutput = recipe.getOutput();
            if (outputStack.isEmpty()) {
                slots.set(outputIndex, expectedOutput.copy());
            } else if (outputStack.isOf(expectedOutput.getItem())) {
                outputStack.increment(1);
            }

            inputStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }
}
