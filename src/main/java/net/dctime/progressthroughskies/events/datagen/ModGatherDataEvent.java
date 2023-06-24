package net.dctime.progressthroughskies.events.datagen;

import net.dctime.progressthroughskies.ProgressThroughSkies;
import net.dctime.progressthroughskies.events.datagen.client.ModItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProgressThroughSkies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModGatherDataEvent
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHH");
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(
                // Tell generator to run only when client assets are generating
                event.includeClient(),
                new ModItemModelProvider(gen, efh)
        );
    }
}
