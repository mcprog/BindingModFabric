package com.mcprog.bindingmod.recipes;

import com.google.gson.JsonObject;
import com.mcprog.bindingmod.BindingMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;

public class PackingTapeRecipeSerializer extends ShapelessRecipe.Serializer {

    public static final PackingTapeRecipeSerializer INSTANCE = new PackingTapeRecipeSerializer();
    public static final Identifier ID = new Identifier(BindingMod.MODID, "packing_tape_recipe");

    @Override
    public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject) {
        return new PackingTapeRecipe(super.read(identifier, jsonObject));
    }

    @Override
    public ShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
        return new PackingTapeRecipe(super.read(identifier, packetByteBuf));
    }


}
