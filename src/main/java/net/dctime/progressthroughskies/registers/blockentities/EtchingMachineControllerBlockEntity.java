package net.dctime.progressthroughskies.registers.blockentities;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.ModBlocks;
import net.dctime.progressthroughskies.registers.ModItems;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineControllerBlock;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineDecoderBlock;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineEncoderBlock;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class EtchingMachineControllerBlockEntity extends BlockEntity
{
    private static final Random rand = new Random();
    private static final Logger LOGGER = LogUtils.getLogger();
    private List<Integer> codeList = new ArrayList<>();
    public EtchingMachineControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ETCHING_MACHINE_CONTROLLER_BLOCK_ENTITY.get(), pPos, pBlockState);

        for (int i = 0; i <= 15; i++) {
            codeList.add(i);
        }
        Collections.shuffle(codeList);
    }

    public List<Integer> getCodeList()
    {
        return codeList;
    }

    public static void tick(Level level, BlockPos pPos, BlockState pState, EtchingMachineControllerBlockEntity entity)
    {
        if (!level.isClientSide())
        {
            // check if structure valid
            if (level.getBlockState(pPos.below()).getBlock() == ModBlocks.ETCHING_MACHINE_DECODER.get() &&
                    level.getBlockState(pPos.above()).getBlock() == ModBlocks.ETCHING_MACHINE_ENCODER.get())
            {
                if (pState.getValue(EtchingMachineControllerBlock.STATE_FLAG) == 0) {
                    level.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 100, 0);
                    level.setBlock(pPos, pState.setValue(EtchingMachineControllerBlock.STATE_FLAG, 1), 3);
                }
            }
            else
            {
                level.setBlock(pPos, pState.setValue(EtchingMachineControllerBlock.STATE_FLAG, 0), 3);
            }

            //check if signal correct
            if (!(pState.getValue(EtchingMachineControllerBlock.STATE_FLAG) == 0))
            {
                Optional<EtchingMachineDecoderBlockEntity> decoder = Optional.empty();
                if (level.getBlockEntity(pPos.below()) instanceof EtchingMachineDecoderBlockEntity)
                {
                    decoder = Optional.ofNullable((EtchingMachineDecoderBlockEntity) level.getBlockEntity(pPos.below()));
                }

                Optional<EtchingMachineEncoderBlockEntity> encoder = Optional.empty();
                if (level.getBlockEntity(pPos.above()) instanceof EtchingMachineEncoderBlockEntity)
                {
                    encoder = Optional.ofNullable((EtchingMachineEncoderBlockEntity) level.getBlockEntity(pPos.above()));
                }

                if (encoder.isPresent() && decoder.isPresent())
                {
                    double x = encoder.get().getBlockPos().getX()+0.5;
                    double y = encoder.get().getBlockPos().getY()+1.5;
                    double z = encoder.get().getBlockPos().getZ()+0.5;

                    ((ServerLevel) level).sendParticles(ParticleTypes.FLAME, x, y, z, 1, 0, 0, 0, 0.02);

//                    LOGGER.debug("G:" + decoder.get().getBlockState().getValue(EtchingMachineDecoderBlock.RECEIVED_POWER) + "S:" + encoder.get().getBlockState().getValue(EtchingMachineEncoderBlock.EMIT_POWER) +
//                            "R" + entity.codeList.get(encoder.get().getBlockState().getValue(EtchingMachineEncoderBlock.EMIT_POWER)));
                    if (decoder.get().getBlockState().getValue(EtchingMachineDecoderBlock.RECEIVED_POWER)
                            .equals(entity.codeList.get(encoder.get().getBlockState().getValue(EtchingMachineEncoderBlock.EMIT_POWER))))
//                    if (true)
                    {
                        if (pState.getValue(EtchingMachineControllerBlock.STATE_FLAG) == 1)
                        {
                            level.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.AMBIENT, 100, 0);
                        }
//                        LOGGER.debug("YEA BABY MACHINE OPENED");

                        level.setBlock(pPos, pState.setValue(EtchingMachineControllerBlock.STATE_FLAG, 2), 3);
                    }
                    else
                    {
                        if (pState.getValue(EtchingMachineControllerBlock.STATE_FLAG) == 2)
                        {
                            level.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.AMBIENT, 100, 0);
                        }
                        level.setBlock(pPos, pState.setValue(EtchingMachineControllerBlock.STATE_FLAG, 1), 3);
                    }
                }
            }
            // if signal correct do stuff
            if (pState.getValue(EtchingMachineControllerBlock.STATE_FLAG) == 2)
            {
                List<Entity> itemEntities = level.getEntities(null,
                        AABB.ofSize(new Vec3(pPos.getX()+0.5, pPos.getY()-1, pPos.getZ()+0.5), 3, 3, 3));
                ((ServerLevel) level).sendParticles(ParticleTypes.ENCHANT, pPos.getX()+2, pPos.getY()+2, pPos.getZ()+2, 1, 0, 0, 0, 0.01);
                ((ServerLevel) level).sendParticles(ParticleTypes.ENCHANT, pPos.getX()-1, pPos.getY()+2, pPos.getZ()+2, 1, 0, 0, 0, 0.01);
                ((ServerLevel) level).sendParticles(ParticleTypes.ENCHANT, pPos.getX()+2, pPos.getY()+2, pPos.getZ()-1, 1, 0, 0, 0, 0.01);
                ((ServerLevel) level).sendParticles(ParticleTypes.ENCHANT, pPos.getX()-1, pPos.getY()+2, pPos.getZ()-1, 1, 0, 0, 0, 0.01);
                // START WORKING
                for (Entity itemEntity : itemEntities)
                {
                    if (itemEntity instanceof ItemEntity && ((ItemEntity) itemEntity).getItem().getItem() == ModItems.BEDROCK_GRAVEL.get())
                    {
                        double x, y, z;
                        int count;
                        x = itemEntity.getX();
                        y = itemEntity.getY();
                        z = itemEntity.getZ();
                        LOGGER.debug("ADDING PARTICLE");
                        ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, x, y, z, 1, 0, 1, 0, 0.01);
                        if (rand.nextInt(720) == 0) {
                            ((ServerLevel) level).sendParticles(ParticleTypes.FLASH, x, y, z, 1, 0, 1, 0, 0.01);
                            count = ((ItemEntity) itemEntity).getItem().getCount();
                            itemEntity.playSound(SoundEvents.ENCHANTMENT_TABLE_USE);
//                            LOGGER.debug("COUNT:" + count);
                            itemEntity.kill();
                            ItemEntity resultEntity = new ItemEntity(level, x, y, z, new ItemStack(Items.AMETHYST_BLOCK, count));
                            level.addFreshEntity(resultEntity);
                        }
                    }
                }
//                LOGGER.debug("Entities:" + itemEntities);
            }
        }
        else
        {
            // SERVER SIDE

        }
    }

    @Override
    public void load(CompoundTag pTag) {
        codeList = Arrays.stream(pTag.getIntArray("code_list")).boxed().collect(Collectors.toList());
        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putIntArray("code_list", codeList);
        super.saveAdditional(pTag);
    }
}
