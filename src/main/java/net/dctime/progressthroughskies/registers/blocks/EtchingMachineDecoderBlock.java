package net.dctime.progressthroughskies.registers.blocks;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.blockentities.EtchingMachineDecoderBlockEntity;
import net.dctime.progressthroughskies.registers.blockentities.EtchingMachineEncoderBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class EtchingMachineDecoderBlock extends BaseEntityBlock
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final IntegerProperty RECEIVED_POWER = IntegerProperty.create("received_power", 0, 15);
    public EtchingMachineDecoderBlock(Properties pProperties) {
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EtchingMachineDecoderBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ETCHING_MACHINE_DECODER_BLOCK_ENTITY.get(), EtchingMachineDecoderBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide())
        {
            pPlayer.sendSystemMessage(Component.literal(((EtchingMachineDecoderBlockEntity) pLevel.getBlockEntity(pPos)).getCodeList().toString()));
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
