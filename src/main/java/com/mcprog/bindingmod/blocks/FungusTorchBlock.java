package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.setup.Registration;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleEffect;

public class FungusTorchBlock extends TorchBlock {
    public FungusTorchBlock() {
        super(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance(14), Registration.FUNGUS_FLAME);
    }


}
