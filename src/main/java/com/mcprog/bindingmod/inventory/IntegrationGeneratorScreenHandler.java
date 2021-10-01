package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.blocks.IntegrationGeneratorBE;
import com.mcprog.bindingmod.blocks.IntegrationGeneratorBlock;
import com.mcprog.bindingmod.recipes.IntegrationRecipe;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class IntegrationGeneratorScreenHandler extends AbstractRecipeScreenHandler<Inventory> {


    public static final int PROCESS_TIME = 100;

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;
    private final PlayerEntity player;
    private final PlayerInventory playerInventory;

    public IntegrationGeneratorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(4));
    }

    public IntegrationGeneratorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(Registration.INTEGRATION_GENERATOR_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 2);
        checkDataCount(propertyDelegate, 4);
        this.propertyDelegate = propertyDelegate;
        this.player = playerInventory.player;
        this.world = player.world;
        this.playerInventory = playerInventory;

        setupMachineSlots();
        setupPlayerSlots();

        this.addProperties(propertyDelegate);
    }

    void setupPlayerSlots() {
        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(this.playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(this.playerInventory, k, 8 + k * 18, 142));
        }
    }

    void setupMachineSlots() {
        this.addSlot(new Slot(this.inventory, 0, 32, 34));
        this.addSlot(new FurnaceOutputSlot(this.player, this.inventory, 1, 129, 34));
    }

    protected boolean isProcessable(ItemStack itemStack) {
        return this.world.getRecipeManager().getFirstMatch(IntegrationRecipe.Type.INSTANCE, new SimpleInventory(new ItemStack[]{itemStack}), this.world).isPresent();
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {

        final int lastInvIndex = 38;
        final int firstPlayerInvIndex = 2;
        final int endPlayerInvIndex = 29;

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 2) {
                if (!this.insertItem(itemStack2, firstPlayerInvIndex, lastInvIndex, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index != 1 && index != 0) {
                if (this.isProcessable(itemStack2)) {
                    if (!this.insertItem(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= firstPlayerInvIndex && index < endPlayerInvIndex) {
                    if (!this.insertItem(itemStack2, endPlayerInvIndex, lastInvIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= endPlayerInvIndex && index < lastInvIndex && !this.insertItem(itemStack2, firstPlayerInvIndex, endPlayerInvIndex, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, firstPlayerInvIndex, lastInvIndex, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

    public int getProcessTime() {
        int timer = propertyDelegate.get(2);
        if (timer <= 0) {
            return 0;
        }
        return timer * 24 / PROCESS_TIME;
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider)this.inventory).provideRecipeInputs(finder);
        }
    }

    @Override
    public void clearCraftingSlots() {
        this.getSlot(0).setStack(ItemStack.EMPTY);
        this.getSlot(1).setStack(ItemStack.EMPTY);
    }

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 1;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 2;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.FURNACE;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 1;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
