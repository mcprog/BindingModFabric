package com.mcprog.bindingmod.setup;

import com.mcprog.bindingmod.BindingMod;
import com.mcprog.bindingmod.blocks.IntegrationGeneratorBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Registration {

    /* ITEMS */
    public static final Item BINDING_CLAY_ITEM = new Item(new FabricItemSettings().group(Tabs.MAIN_TAB));

    /* BLOCKS */
    public static final Block INTEGRATION_GENERATOR_BLOCK = new IntegrationGeneratorBlock();

    public static void registerItems() {
        registerItem(BINDING_CLAY_ITEM, "binding_clay_item");
    }

    public static void registerBlocks() {
        registerBlock(INTEGRATION_GENERATOR_BLOCK, "integration_generator", Tabs.MAIN_TAB);
    }

    private static void registerItem(Item item, String name) {
        Registry.register(Registry.ITEM, new Identifier(BindingMod.MODID, name), item);
    }

    private static void registerBlock(Block block, String rawName, ItemGroup tab) {
        Registry.register(Registry.BLOCK, new Identifier(BindingMod.MODID, rawName + "_block"), block);
        registerItem(new BlockItem(block, new FabricItemSettings().group(tab)), rawName + "_item");
    }
}
