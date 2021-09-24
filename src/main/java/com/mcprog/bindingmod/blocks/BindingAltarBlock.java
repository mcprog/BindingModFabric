package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.setup.Registration;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BindingAltarBlock extends Block implements BlockEntityProvider {

    public BindingAltarBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(3).nonOpaque());
    }

    static final int BINDING_SLOT = 0;
    static final int INPUT_SLOT = 1;
    static final int CATALYST_SLOT = 2;
    static final int OUTPUT_SLOT = 3;

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BindingAltarBE(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        Inventory blockInventory = (Inventory) world.getBlockEntity(pos);
        ItemStack playerHandStack = player.getStackInHand(hand);

        if (!playerHandStack.isEmpty()) {
            if (blockInventory.getStack(BINDING_SLOT).isEmpty() && playerHandStack.isOf(Registration.BINDING_CLAY_ITEM)) {
                InventoryBE.transferStackIn(blockInventory, BINDING_SLOT, playerHandStack);
            }
        } else {
            for (int i = OUTPUT_SLOT; i >= BINDING_SLOT; i--) {
                if (!blockInventory.getStack(i).isEmpty()) {
                    InventoryBE.transferStackOut(blockInventory, player.getInventory(), i);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.SUCCESS;
    }

}
