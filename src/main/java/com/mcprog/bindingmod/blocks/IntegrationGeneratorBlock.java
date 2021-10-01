package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.BindingMod;
import com.mcprog.bindingmod.inventory.SimpleScreenHandler;
import com.mcprog.bindingmod.setup.Registration;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class IntegrationGeneratorBlock extends AbstractFurnaceBlock implements BlockEntityProvider {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    public IntegrationGeneratorBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(3.5f).luminance(createLightLevelFromPoweredBlockState(13)));
    }

    private static ToIntFunction<BlockState> createLightLevelFromPoweredBlockState(int lightLevel) {
        return (state) -> (Boolean)state.get(Properties.LIT) ? lightLevel : 0;
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof IntegrationGeneratorBE) {
            player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);

        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IntegrationGeneratorBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }
        return checkType(type, Registration.INTEGRATION_GENERATOR_BE, IntegrationGeneratorBE::tick);
    }



}
