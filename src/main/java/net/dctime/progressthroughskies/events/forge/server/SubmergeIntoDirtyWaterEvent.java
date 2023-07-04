package net.dctime.progressthroughskies.events.forge.server;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModFluidTypes;
import net.dctime.progressthroughskies.registers.ModSoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
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
        if (!entity.level.isClientSide() && entity.isInFluidType(ModFluidTypes.DUSTED_WATER.get()))
        {
            entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 24, 1));
            // entity.playSound(ModSoundEvents.DUSTED_WATER_HURT_EYE.get());
        }

    }
}
