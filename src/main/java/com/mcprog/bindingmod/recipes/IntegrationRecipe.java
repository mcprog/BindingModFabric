package com.mcprog.bindingmod.recipes;

import com.mcprog.bindingmod.blocks.IntegrationGeneratorBE;
import com.mcprog.bindingmod.blocks.IntegrationGeneratorBlock;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

public class IntegrationRecipe extends AbstractCookingRecipe {

    private final Ingredient input;
    private final ItemStack result;
    private final Identifier id;

    public static final int PROCESS_TIME = 100;

    public IntegrationRecipe(Identifier id, Ingredient input, ItemStack result) {
        super(Type.INSTANCE, id, "bindingmod:integration_recipe", input, result, 0, PROCESS_TIME);
        this.id = id;
        this.input = input;
        this.result = result;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Registration.INTEGRATION_GENERATOR_BLOCK);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IntegrationRecipeSerializer.INSTANCE;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public static class Type implements RecipeType<IntegrationRecipe>, CustomRecipeType {

        private Type() {} // singleton
        public static final Type INSTANCE = new Type();

        public static final String ID = "integration_recipe";

        @Override
        public CustomRecipeType getInstance() {
            return INSTANCE;
        }
    }
}
