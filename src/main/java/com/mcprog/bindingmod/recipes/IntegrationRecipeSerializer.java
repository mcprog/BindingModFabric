package com.mcprog.bindingmod.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mcprog.bindingmod.BindingMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IntegrationRecipeSerializer implements RecipeSerializer<IntegrationRecipe> {

    private IntegrationRecipeSerializer() {} // singleton

    public static final IntegrationRecipeSerializer INSTANCE = new IntegrationRecipeSerializer();
    public static final Identifier ID = new Identifier(BindingMod.MODID, "integration_recipe");

    @Override
    public IntegrationRecipe read(Identifier id, JsonObject json) {
        IntegrationRecipeJsonFormat recipeJson = new Gson().fromJson(json, IntegrationRecipeJsonFormat.class);
        if (recipeJson.input == null || recipeJson.result == null) {
            throw new JsonSyntaxException("A required attribute is missing from an IntegrationRecipe");
        }

        Ingredient input = Ingredient.fromJson(recipeJson.input);
        Item resultItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                .orElseThrow(() -> new JsonSyntaxException("No such item: " + recipeJson.result));
        ItemStack result = new ItemStack(resultItem, 1);
        return new IntegrationRecipe(id, input, result);
    }

    @Override
    public IntegrationRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient input = Ingredient.fromPacket(buf);
        ItemStack result = buf.readItemStack();
        return new IntegrationRecipe(id, input, result);
    }

    @Override
    public void write(PacketByteBuf buf, IntegrationRecipe recipe) {
        recipe.getInput();
        buf.writeItemStack(recipe.getOutput());
    }

    private class IntegrationRecipeJsonFormat {
        JsonObject input;
        String result;
    }
}
