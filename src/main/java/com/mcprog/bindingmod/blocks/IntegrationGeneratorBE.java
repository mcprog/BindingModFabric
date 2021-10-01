package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.inventory.CraftingUtility;
import com.mcprog.bindingmod.inventory.IntegrationGeneratorScreenHandler;
import com.mcprog.bindingmod.inventory.SimpleInventory;
import com.mcprog.bindingmod.recipes.IntegrationRecipe;
import com.mcprog.bindingmod.setup.Registration;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IntegrationGeneratorBE extends AbstractFurnaceBlockEntity {

    public static final int INV_SIZE = 2;
    public static final int PROCESS_TIME = 100;

    public int processTimer;
    public int burnTimer;



    public IntegrationGeneratorBE(BlockPos pos, BlockState state) {
        super(Registration.INTEGRATION_GENERATOR_BE, pos, state, IntegrationRecipe.Type.INSTANCE);
        processTimer = this.propertyDelegate.get(2);
        burnTimer = this.propertyDelegate.get(0);

    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory inv) {
        IntegrationGeneratorScreenHandler handler = new IntegrationGeneratorScreenHandler(syncId, inv, this, this.propertyDelegate);
        return handler;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        this.propertyDelegate.set(2, processTimer);
        this.propertyDelegate.set(0, burnTimer);
        return super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, IntegrationGeneratorBE blockEntity) {

        ItemStack inputStack = blockEntity.inventory.get(0);
        ItemStack outputStack = blockEntity.inventory.get(1);

        boolean dirtyGurl = false;
        blockEntity.burnTimer--;

        if (inputStack.isEmpty()) {
            if (blockEntity.processTimer > 0) {
                blockEntity.processTimer = MathHelper.clamp(blockEntity.processTimer - 2, 0, PROCESS_TIME);
            }
        } else {
            IntegrationRecipe recipe = world.getRecipeManager().getFirstMatch(IntegrationRecipe.Type.INSTANCE, blockEntity, world).orElse(null);
            int maxStackSize = blockEntity.getMaxCountPerStack();
            if (BlockEntityUtility.canAcceptRecipeOutput(recipe, inputStack, outputStack, maxStackSize)) {
                ++blockEntity.processTimer;
                if (blockEntity.processTimer == PROCESS_TIME) {
                    blockEntity.processTimer = 0;
                    if (CraftingUtility.craftRecipe(recipe, blockEntity.inventory, 0, 1, maxStackSize)) {
                        // successfully crafted recipe
                    }
                    blockEntity.burnTimer = 2;
                    dirtyGurl = false;
                }
            } else {
                blockEntity.processTimer = 0;
            }
        }

        if (blockEntity.burnTimer <= 0) {
            dirtyGurl = true;
            state = (BlockState)state.with(AbstractFurnaceBlock.LIT, blockEntity.processTimer > 0);
            world.setBlockState(pos, state, 3);
        }

        if (dirtyGurl) {
            markDirty(world, pos, state);
        }

        if (blockEntity.processTimer != blockEntity.propertyDelegate.get(2)) {
            blockEntity.propertyDelegate.set(2, blockEntity.processTimer);
        }
    }
}
