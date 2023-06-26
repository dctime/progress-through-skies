package net.dctime.progressthroughskies.registers.items;

import com.mojang.logging.LogUtils;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class RainWandItem extends Item
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int RAIN_DURATION = 6000;
    public RainWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide())
        {
            LOGGER.debug("Summoning rain Rain Wand!");
            ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
            if (!pPlayer.isCreative())
            {
                itemStack.shrink(1);
            }
            for (ServerLevel serverLevel : pPlayer.getLevel().getServer().getAllLevels())
            {
                if (serverLevel.players().contains(pPlayer))
                {
                    LOGGER.debug("Found player at level" + serverLevel);
                    startRaining(serverLevel, pPlayer);
                }
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("tooltip.rain_wand.info"));
        pTooltipComponents.add(Component.translatable("tooltip.rain_wand.warn"));
    }

    private void startRaining(ServerLevel serverLevel, Player player)
    {
        serverLevel.setWeatherParameters(0, RAIN_DURATION, true, false);
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
        lightningbolt.moveTo(Vec3.atCenterOf(new BlockPos(player.getX(), player.getY()+2, player.getZ())));
        serverLevel.addFreshEntity(lightningbolt);

    }
}
