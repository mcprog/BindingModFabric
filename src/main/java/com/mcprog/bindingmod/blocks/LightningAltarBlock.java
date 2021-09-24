package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.BindingMod;
import com.mcprog.bindingmod.setup.Registration;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LightningAltarBlock extends Block implements BlockEntityProvider {

    public static final int INPUT_SLOT = 0;
    static final int OUTPUT_SLOT = 1;

    public static final Tag<Item> COPPER_COMPONENTS = TagRegistry.item(new Identifier(BindingMod.MODID, "copper_components"));

    public LightningAltarBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(500, 6).requiresTool().nonOpaque());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightningAltarBE(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        Inventory blockInventory = (Inventory) world.getBlockEntity(pos);
        ItemStack playerHandStack = player.getStackInHand(hand);

        if (!playerHandStack.isEmpty()) {
            if (blockInventory.getStack(INPUT_SLOT).isEmpty() && playerHandStack.isIn(COPPER_COMPONENTS)) {
                InventoryBE.transferStackIn(blockInventory, INPUT_SLOT, playerHandStack);
            }
        } else {
           if (world.isRaining() && playerHandStack.isOf(Items.LIGHTNING_ROD)) {

           }
        }
        return ActionResult.SUCCESS;
    }
}
