package net.dctime.progressthroughskies.registers.blockentities;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineEncoderBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Random;

public class EtchingMachineEncoderBlockEntity extends BlockEntity
{
    private static final Random RAND = new Random();
    private static final Logger LOGGER = LogUtils.getLogger();
    public final int DELAY_TIMER = 10;
    private int currentTimer;
    public EtchingMachineEncoderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ETCHING_MACHINE_ENCODER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EtchingMachineEncoderBlockEntity entity)
    {
//        LOGGER.debug(String.valueOf(entity.currentTimer));
        if (!level.isClientSide())
        {
            if (entity.currentTimer == entity.DELAY_TIMER)
            {
                // DO STUFF
                level.setBlock(pos, state.setValue(EtchingMachineEncoderBlock.EMIT_POWER, RAND.nextInt(16)), 3);
                // end
                entity.currentTimer = 0;
            }
            else
            {
                entity.currentTimer++;
            }
        }
        else
        {
            return;
        }
    }
}
