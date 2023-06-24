package net.dctime.progressthroughskies.events.datagen.client;

import net.dctime.progressthroughskies.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider
{

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ProgressThroughSkies.MODID, existingFileHelper);
        System.out.println("hey");
    }

    @Override
    protected void registerModels() {
        this.basicItem(ModItems.BEDROCK_DUST.get());
    }
}
