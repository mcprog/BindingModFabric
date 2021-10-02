package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.inventory.IntegrationGeneratorScreenHandler;
import com.mcprog.bindingmod.inventory.SimpleInventory;
import com.mcprog.bindingmod.inventory.StorageCrateScreenHandler;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class StorageCrateBE extends InventoryBE implements NamedScreenHandlerFactory {


    public static int INV_SIZE = 27;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);

    public StorageCrateBE(BlockPos pos, BlockState state) {
        super(Registration.STORAGE_CRATE_BE, pos, state);

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        StorageCrateScreenHandler handler = new StorageCrateScreenHandler(syncId, inv, this);
        return handler;
    }
}
