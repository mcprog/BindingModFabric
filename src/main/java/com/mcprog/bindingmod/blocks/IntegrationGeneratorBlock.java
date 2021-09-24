package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.BindingMod;
import com.mcprog.bindingmod.inventory.SimpleScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
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

public class IntegrationGeneratorBlock extends HorizontalFacingBlock implements BlockEntityProvider {

    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    public IntegrationGeneratorBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(3.5f).luminance(createLightLevelFromPoweredBlockState(13)));
        setDefaultState(getStateManager().getDefaultState().with(POWERED, false).with(FACING, Direction.NORTH));
    }

    private static ToIntFunction<BlockState> createLightLevelFromPoweredBlockState(int lightLevel) {
        return (state) -> (Boolean)state.get(Properties.POWERED) ? lightLevel : 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(POWERED, false);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory factory = SimpleScreenHandler.createScreenHandlerFactory(state, world, pos);
            if (factory != null) {
                player.openHandledScreen(factory);
            }
        }

        return ActionResult.SUCCESS;
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IntegrationGeneratorBE(pos, state);
    }
}
