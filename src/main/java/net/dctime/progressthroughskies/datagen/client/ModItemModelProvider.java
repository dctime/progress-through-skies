package net.dctime.progressthroughskies.datagen.client;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider
{

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ProgressThroughSkies.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        this.basicItem(ModItems.BEDROCK_DUST.get());
        this.basicItem(ModItems.SAND_DUST.get());
        this.basicItem(ModItems.GRAVEL_DUST.get());
        this.basicItem(ModItems.RAIN_WAND.get());
    }
}
