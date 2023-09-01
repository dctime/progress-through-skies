package net.dctime.progressthroughskies.registers.blocks;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.slf4j.Logger;

public class EtchingMachineBlock extends Block
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final IntegerProperty RECEIVED_POWER = IntegerProperty.create("received_power", 0, 15);
    public EtchingMachineBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(RECEIVED_POWER, 0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        this.defaultBlockState().setValue(RECEIVED_POWER, pContext.getLevel().getBestNeighborSignal(pContext.getClickedPos()));
        return this.defaultBlockState();
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            LOGGER.info(String.valueOf(pLevel.getBestNeighborSignal(pPos)));
            pLevel.setBlock(pPos, pState.setValue(RECEIVED_POWER, pLevel.getBestNeighborSignal(pPos)), 2);

            LOGGER.debug(String.valueOf(pState.getValue(RECEIVED_POWER)));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(RECEIVED_POWER);
    }
}
