package net.dctime.progressthroughskies.events.forge.server;

import net.dctime.progressthroughskies.capabilities.PlayerScreaming;
import net.dctime.progressthroughskies.capabilities.PlayerScreamingProvider;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModFluidTypes;
import net.dctime.progressthroughskies.registers.ModSoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProgressThroughSkies.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SubmergeIntoDirtyWaterEvent
{
    @SubscribeEvent
    public static void submergeIntoDirtyWater(LivingEvent.LivingTickEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player)
        {
            Player player = (Player) event.getEntity();
            player.getCapability(PlayerScreamingProvider.PLAYER_SCREAMING).ifPresent(cap ->
            {
                cap.tick();
                if (player.isInFluidType(ModFluidTypes.DUSTED_WATER.get()))
                {
                    if (!cap.isScreaming())
                    {
                        System.out.println("AHHHHHH");
                        cap.startScreaming();
                        player.playSound(ModSoundEvents.DUSTED_WATER_HURT_EYE.get());
                        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 312, 1));
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 312, 1));
                    }
                }

            });
        }



    }
}
