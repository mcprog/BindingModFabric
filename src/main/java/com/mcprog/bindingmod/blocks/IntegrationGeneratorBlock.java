package com.mcprog.bindingmod.blocks;

import com.mcprog.bindingmod.BindingMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class IntegrationGeneratorBlock extends HorizontalFacingBlock {

    public static final BooleanProperty POWERED = Properties.POWERED;

    // FURNACE = register("furnace", new FurnaceBlock(Settings.of(Material.STONE).requiresTool().strength(3.5F).luminance(createLightLevelFromLitBlockState(13))));

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


}
