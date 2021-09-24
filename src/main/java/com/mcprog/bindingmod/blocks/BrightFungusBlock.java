package com.mcprog.bindingmod.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class BrightFungusBlock extends FungusBlock {
    public BrightFungusBlock() {
        super(FabricBlockSettings.of(Material.PLANT, MapColor.DARK_RED).breakInstantly().noCollision().sounds(BlockSoundGroup.FUNGUS)
                .luminance((state) -> {return 14;}), () -> {
            return ConfiguredFeatures.CRIMSON_FUNGI_PLANTED;
        });
    }
}
