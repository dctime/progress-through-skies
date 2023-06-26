package net.dctime.progressthroughskies.datagen.client;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModBlocks;
import net.dctime.progressthroughskies.registers.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLanguageProvider extends LanguageProvider
{

    public ModEnUsLanguageProvider(DataGenerator gen, String locale) {
        super(gen, ProgressThroughSkies.MODID, locale);
    }

    @Override
    protected void addTranslations()
    {
        this.addItem(ModItems.DUSTED_BEDROCK, "Dusted Bedrock");
        this.addItem(ModItems.BEDROCK_DUST, "Bedrock Dust");
        this.add("itemGroup.progress_through_skies", "Progress Through Skies"); // Creative Mod Tab
        this.addItem(ModItems.BEDROCK_GRAVEL, "Bedrock Gravel");
        this.addItem(ModItems.SAND_DUST, "Sand Dust");
        this.addItem(ModItems.GRAVEL_DUST, "Gravel Dust");
        this.addItem(ModItems.RAIN_WAND, "Rain Wand");
        this.add("tooltip.rain_wand.info", "&7Summons rain by right clicking it");
        this.add("tooltip.rain_wand.warn", "&l&4&o&nStrike lightning on your head for not having patience");
    }
}
