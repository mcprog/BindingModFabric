package com.mcprog.bindingmod.recipes;

import com.mcprog.bindingmod.blocks.IntegrationGeneratorBE;
import com.mcprog.bindingmod.blocks.IntegrationGeneratorBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

public class IntegrationRecipe implements Recipe<CraftingInventory> {

    private final Ingredient input;
    private final ItemStack result;
    private final Identifier id;

    public IntegrationRecipe(Identifier id, Ingredient input, ItemStack result) {
        this.id = id;
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        if (inventory.size() < IntegrationGeneratorBE.INV_SIZE) {
            return false;
        }
        return input.test(inventory.getStack(IntegrationGeneratorBlock.INPUT_SLOT));
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        return result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IntegrationRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<IntegrationRecipe> {

        private Type() {} // singleton
        public static final Type INSTANCE = new Type();

        public static final String ID = "integration_recipe";
    }
}
