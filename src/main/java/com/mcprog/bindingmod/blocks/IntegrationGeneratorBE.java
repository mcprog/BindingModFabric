package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.inventory.IntegrationGeneratorScreenHandler;
import com.mcprog.bindingmod.inventory.SimpleInventory;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class IntegrationGeneratorBE extends BlockEntity implements SimpleInventory, NamedScreenHandlerFactory {

    public static final int INV_SIZE = 2;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);

    public IntegrationGeneratorBE(BlockPos pos, BlockState state) {
        super(Registration.INTEGRATION_GENERATOR_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    /*
     * TODO inherit from InventoryBE instead
     *  Confirmed that InventoryBE works with BindingAltarBE
     */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        return super.writeNbt(nbt);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        IntegrationGeneratorScreenHandler handler = new IntegrationGeneratorScreenHandler(syncId, inv, this, ScreenHandlerContext.create(world, pos));
        return handler;
    }
}
