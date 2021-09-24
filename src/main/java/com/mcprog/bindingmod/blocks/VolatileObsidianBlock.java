package com.mcprog.bindingmod.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class VolatileObsidianBlock extends Block {

    public VolatileObsidianBlock() {
        super(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(50, 6).luminance((state) -> {
            return 10;
        }));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1.5f, DestructionType.DESTROY);

        return ActionResult.SUCCESS;
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }
}
