package net.dctime.progressthroughskies.registers.blocks;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.blockentities.AdderBlockEntity;
import net.dctime.progressthroughskies.registers.blockentities.NegatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class NegatorBlock extends BaseEntityBlock
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public NegatorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new NegatorBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        boolean isValid =
                // First Layer
                isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 0, -2, 0) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 1, -2, 0) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 0, -2, 1) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, -1, -2, 0) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 0, -2, -1) &&
                        // Second Layer
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 2, -1, 0) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, -2, -1, 0) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 0, -1, 2) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 0, -1, -2) &&
                        //Third Layer
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 2, 0, 0) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, -2, 0, 0) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 0, 0, 2) &&
                        isBlockRightThere(pLevel, pPos, Blocks.GRAY_WOOL, 0, 0, -2) &&

                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, 3, 0, 3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, 2, 0, 3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, 3, 0, 2) &&

                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, 3, 0, -3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, 2, 0, -3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, 3, 0, -2) &&

                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, -3, 0, 3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, -2, 0, 3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, -3, 0, 2) &&

                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, -3, 0, -3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, -2, 0, -3) &&
                        isBlockRightThere(pLevel, pPos, Blocks.BLUE_WOOL, -3, 0, -2);


        if (!pLevel.isClientSide() && isValid)
        {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof NegatorBlockEntity)
            {
//                LOGGER.debug("use activated");
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (NegatorBlockEntity)entity, pPos);
            }
            else
            {
                throw new IllegalStateException("Negator Container provider is missing");
            }
        }
        return InteractionResult.SUCCESS;
    }

    private boolean isBlockRightThere(Level level, BlockPos pos, Block block, int xOffset, int yOffset, int zOffset)
    {
        if (!level.isClientSide())
        {
            return level.getBlockState(new BlockPos(pos.getX()+xOffset, pos.getY()+yOffset, pos.getZ()+zOffset)).getBlock() == block;
        }

        return false;
    }
}
