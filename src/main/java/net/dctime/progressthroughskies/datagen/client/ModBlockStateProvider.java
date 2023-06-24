package net.dctime.progressthroughskies.datagen.client;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider
{

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ProgressThroughSkies.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(ModBlocks.DUSTED_BEDROCK.get());
        this.simpleBlockItem(ModBlocks.DUSTED_BEDROCK.get(), models().getExistingFile(modLoc("block/dusted_bedrock")));
    }
}
