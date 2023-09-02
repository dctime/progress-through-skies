package net.dctime.progressthroughskies.registers.blockentities;

import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineDecoderBlock;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineEncoderBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EtchingMachineDecoderBlockEntity extends BlockEntity
{

    public EtchingMachineDecoderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ETCHING_MACHINE_DECODER_BLOCK_ENTITY.get(), pPos, pBlockState);

    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, EtchingMachineDecoderBlockEntity entity)
    {
        if (!level.isClientSide())
        {
            return;
        }
        else
        {
            return;
        }
    }
}
