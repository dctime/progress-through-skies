package net.dctime.progressthroughskies.registers.blocks;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Random;

public class DustedBedrockBlock extends Block
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public DustedBedrockBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        LOGGER.debug("OUCH");
        ItemStack spawnItemStack = new ItemStack(ModItems.BEDROCK_DUST.get());
        Random random = new Random();
        if (random.nextInt(2) == 1 && pPlayer.isShiftKeyDown())
        {
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX()+0.5, pPos.getY()+1, pPos.getZ()+0.5, spawnItemStack));
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
