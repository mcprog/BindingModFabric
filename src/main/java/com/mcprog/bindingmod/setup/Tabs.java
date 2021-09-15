package com.mcprog.bindingmod.setup;

import com.mcprog.bindingmod.BindingMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Tabs {

    public static final ItemGroup MAIN_TAB = FabricItemGroupBuilder.build(
            new Identifier(BindingMod.MODID, "main"), () -> new ItemStack(Registration.BINDING_CLAY_ITEM)
    );
}
