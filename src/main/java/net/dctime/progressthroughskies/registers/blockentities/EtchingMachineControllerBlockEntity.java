package net.dctime.progressthroughskies.registers.blockentities;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.ModBlocks;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineControllerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

public class EtchingMachineControllerBlockEntity extends BlockEntity
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public EtchingMachineControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ETCHING_MACHINE_CONTROLLER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pPos, BlockState pState, EtchingMachineControllerBlockEntity entity)
    {
        if (!level.isClientSide())
        {
            if (level.getBlockState(pPos.below()).getBlock() == ModBlocks.ETCHING_MACHINE_DECODER.get() &&
                    level.getBlockState(pPos.above()).getBlock() == ModBlocks.ETCHING_MACHINE_ENCODER.get())
            {
                if (pState.getValue(EtchingMachineControllerBlock.STATE_FLAG) == 0) {
                    level.setBlock(pPos, pState.setValue(EtchingMachineControllerBlock.STATE_FLAG, 1), 3);
                }
            }
            else
            {
                level.setBlock(pPos, pState.setValue(EtchingMachineControllerBlock.STATE_FLAG, 0), 3);
            }
        }
    }
}
