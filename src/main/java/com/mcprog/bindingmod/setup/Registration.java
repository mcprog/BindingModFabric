package com.mcprog.bindingmod.setup;

import com.mcprog.bindingmod.BindingMod;
import com.mcprog.bindingmod.blocks.*;
import com.mcprog.bindingmod.inventory.IntegrationGeneratorScreenHandler;
import com.mcprog.bindingmod.recipes.IntegrationRecipe;
import com.mcprog.bindingmod.recipes.IntegrationRecipeSerializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Registration {

    /* UNIVERSAL IDENTIFIERS */
    private static final Identifier INTEGRATION_GENERATOR_ID = new Identifier(BindingMod.MODID, "integration_generator");
    private static final Identifier BINDING_ALTAR_ID = new Identifier(BindingMod.MODID, "binding_altar");
    private static final Identifier LIGHTNING_ALTAR_ID = new Identifier(BindingMod.MODID, "lightning_altar");

    /* PARTICLES */
    public static final DefaultParticleType FUNGUS_FLAME = FabricParticleTypes.simple();

    /* ITEMS */
    public static final Item BINDING_CLAY_ITEM = new Item(new FabricItemSettings().group(Tabs.MAIN_TAB));

    /* BLOCKS */
    public static final Block INTEGRATION_GENERATOR_BLOCK = new IntegrationGeneratorBlock();
    public static final Block BINDING_ALTAR_BLOCK = new BindingAltarBlock();
    public static final Block VOLATILE_OBSIDIAN_BLOCK = new VolatileObsidianBlock();
    public static final Block BRIGHT_FUNGUS = new BrightFungusBlock();
    public static final Block LIGHTNING_ALTAR_BLOCK = new LightningAltarBlock();
    public static final Block FUNGUS_TORCH_BLOCK = new FungusTorchBlock();


    /* BLOCK ENTITIES */
    public static BlockEntityType<IntegrationGeneratorBE> INTEGRATION_GENERATOR_BE;
    public static BlockEntityType<BindingAltarBE> BINDING_ALTAR_BE;
    public static BlockEntityType<LightningAltarBE> LIGHTNING_ALTAR_BE;

    /* SCREEN HANDLERS */
    public static ScreenHandlerType<IntegrationGeneratorScreenHandler> INTEGRATION_GENERATOR_SCREEN_HANDLER;




    public static void registerItems() {
        registerItem(BINDING_CLAY_ITEM, "binding_clay_item");
    }

    public static void registerBlocks() {
        registerBlock(INTEGRATION_GENERATOR_BLOCK, INTEGRATION_GENERATOR_ID, Tabs.MAIN_TAB);
        registerBlock(BINDING_ALTAR_BLOCK, BINDING_ALTAR_ID, Tabs.MAIN_TAB);
        registerBlock(VOLATILE_OBSIDIAN_BLOCK, new Identifier(BindingMod.MODID, "volatile_obsidian"), Tabs.MAIN_TAB);
        registerBlock(BRIGHT_FUNGUS, new Identifier(BindingMod.MODID, "bright_fungus"), Tabs.MAIN_TAB);
        registerBlock(LIGHTNING_ALTAR_BLOCK, LIGHTNING_ALTAR_ID, Tabs.MAIN_TAB);
        registerBlock(FUNGUS_TORCH_BLOCK, new Identifier(BindingMod.MODID, "fungus_torch"), Tabs.MAIN_TAB);
    }

    public static void registerBlockEntities() {
        INTEGRATION_GENERATOR_BE = Registry.register(Registry.BLOCK_ENTITY_TYPE, INTEGRATION_GENERATOR_ID,
                FabricBlockEntityTypeBuilder.create(IntegrationGeneratorBE::new, INTEGRATION_GENERATOR_BLOCK).build(null));
        BINDING_ALTAR_BE = Registry.register(Registry.BLOCK_ENTITY_TYPE, BINDING_ALTAR_ID,
                FabricBlockEntityTypeBuilder.create(BindingAltarBE::new, BINDING_ALTAR_BLOCK).build(null));
        LIGHTNING_ALTAR_BE = Registry.register(Registry.BLOCK_ENTITY_TYPE, LIGHTNING_ALTAR_ID,
                FabricBlockEntityTypeBuilder.create(LightningAltarBE::new, LIGHTNING_ALTAR_BLOCK).build(null));
    }

    public static void registerScreenHandlers() {
        INTEGRATION_GENERATOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(INTEGRATION_GENERATOR_ID, IntegrationGeneratorScreenHandler::new);
    }

    public static void registerParticleTypes() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(BindingMod.MODID, "fungus_flame"), FUNGUS_FLAME);
    }

    public static void registerRecipeTypes() {
        Registry.register(Registry.RECIPE_SERIALIZER, IntegrationRecipeSerializer.ID, IntegrationRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(BindingMod.MODID, IntegrationRecipe.Type.ID), IntegrationRecipe.Type.INSTANCE);
    }

    private static void registerItem(Item item, String name) {
        registerItem(item, new Identifier(BindingMod.MODID, name));
    }

    private static void registerItem(Item item, Identifier id) {
        Registry.register(Registry.ITEM, id, item);
    }

    private static void registerBlock(Block block, Identifier id, ItemGroup tab) {
        Registry.register(Registry.BLOCK, id, block);
        registerItem(new BlockItem(block, new FabricItemSettings().group(tab)), id);
    }

}
