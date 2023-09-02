package net.dctime.progressthroughskies.datagen.client;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModBlocks;
import net.dctime.progressthroughskies.registers.blocks.EtchingMachineControllerBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
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

        ModelFile adderModelFile = this.models().cube("adder",
                new ResourceLocation(ProgressThroughSkies.MODID, "block/adder/adder_down"),
                new ResourceLocation(ProgressThroughSkies.MODID,"block/adder/adder_left_up"),
                new ResourceLocation(ProgressThroughSkies.MODID,"block/adder/adder_front"),
                new ResourceLocation(ProgressThroughSkies.MODID,"block/adder/adder_blank"),
                new ResourceLocation(ProgressThroughSkies.MODID,"block/adder/adder_left_up"),
                new ResourceLocation(ProgressThroughSkies.MODID,"block/adder/adder_right"));
        this.horizontalBlock(ModBlocks.ADDER.get(), adderModelFile);
        this.simpleBlockItem(ModBlocks.ADDER.get(), adderModelFile);


        this.basicBlock(ModBlocks.ETCHING_MACHINE_ENCODER.get(), "etching_machine_encoder");
        this.basicBlock(ModBlocks.ETCHING_MACHINE_DECODER.get(), "etching_machine_decoder");

        ModelFile etchingMachineControllerRed = this.models().cube("etching_machine_controller_red",
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_top"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_top"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_red"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_red"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_red"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_red"));

        ModelFile etchingMachineControllerYellow = this.models().cube("etching_machine_controller_yellow",
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_top"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_top"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_yellow"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_yellow"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_yellow"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_yellow"));

        ModelFile etchingMachineControllerGreen = this.models().cube("etching_machine_controller_green",
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_top"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_top"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_green"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_green"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_green"),
                new ResourceLocation(ProgressThroughSkies.MODID, "block/etching_machine/etching_machine_pillar_green"));

        this.getVariantBuilder(ModBlocks.ETCHING_MACHINE_CONTROLLER.get())
                .partialState()
                .with(EtchingMachineControllerBlock.STATE_FLAG, 0)
                    .modelForState()
                    .modelFile(etchingMachineControllerRed)
                    .addModel()
                .partialState()
                .with(EtchingMachineControllerBlock.STATE_FLAG, 1)
                    .modelForState()
                    .modelFile(etchingMachineControllerYellow)
                    .addModel()
                .partialState()
                .with(EtchingMachineControllerBlock.STATE_FLAG, 2)
                    .modelForState()
                    .modelFile(etchingMachineControllerGreen)
                    .addModel();


    }

    private void basicBlock(Block block, String name)
    {
        this.simpleBlock(block);
        this.simpleBlockItem(block, models().getExistingFile(modLoc("block/" + name)));
    }
}
