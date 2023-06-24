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
    }
}
