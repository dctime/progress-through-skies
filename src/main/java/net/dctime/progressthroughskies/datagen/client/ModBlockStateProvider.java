package net.dctime.progressthroughskies.datagen.client;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider
{

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ProgressThroughSkies.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.basicBlock(ModBlocks.DUSTED_BEDROCK.get(), "dusted_bedrock");
        this.basicBlock(ModBlocks.BEDROCK_GRAVEL.get(), "bedrock_gravel");
    }

    private void basicBlock(Block block, String name)
    {
        this.simpleBlock(block);
        this.simpleBlockItem(block, models().getExistingFile(modLoc("block/" + name)));
    }
}
