package net.dctime.progressthroughskies.events.mod;

import net.dctime.progressthroughskies.datagen.client.ModBlockStateProvider;
import net.dctime.progressthroughskies.datagen.client.ModEnUsLanguageProvider;
import net.dctime.progressthroughskies.datagen.client.ModItemModelProvider;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
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
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(
                // Tell generator to run only when client assets are generating
                event.includeClient(),
                new ModItemModelProvider(gen, efh)
        );

        gen.addProvider(
                event.includeClient(),
                new ModEnUsLanguageProvider(gen, "en_us")
        );

        gen.addProvider(
                event.includeClient(),
                new ModBlockStateProvider(gen, efh)
        );
    }
}