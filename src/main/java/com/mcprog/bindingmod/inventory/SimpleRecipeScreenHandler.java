package com.mcprog.bindingmod.inventory;

import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public abstract class SimpleRecipeScreenHandler extends AbstractRecipeScreenHandler<Inventory> {

    protected final Inventory inventory;
    protected final PropertyDelegate propertyDelegate;
    protected final World world;
    protected final PlayerEntity player;
    protected final PlayerInventory playerInventory;

    public SimpleRecipeScreenHandler(ScreenHandlerType type, int syncId, PlayerInventory playerInventory, int expectedInvSize, int expectedDataSize) {
        this(type, syncId, playerInventory, new SimpleInventory(expectedInvSize), new ArrayPropertyDelegate(expectedDataSize), expectedInvSize, expectedDataSize);
    }

    public SimpleRecipeScreenHandler(ScreenHandlerType type, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate, int expectedInvSize, int expectedDataSize) {
        super(type, syncId);
        this.inventory = inventory;
        checkSize(inventory, expectedInvSize);
        checkDataCount(propertyDelegate, expectedDataSize);
        this.propertyDelegate = propertyDelegate;
        this.player = playerInventory.player;
        this.world = player.world;
        this.playerInventory = playerInventory;

        setupMachineSlots();
        setupPlayerSlots();

        this.addProperties(propertyDelegate);
    }

    protected abstract void setupMachineSlots();

    public void setupPlayerSlots() {
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

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider)this.inventory).provideRecipeInputs(finder);
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
