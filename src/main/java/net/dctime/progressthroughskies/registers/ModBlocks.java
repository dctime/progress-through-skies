package net.dctime.progressthroughskies.registers;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProgressThroughSkies.MODID);

    public static final RegistryObject<Block> DUSTED_BEDROCK = BLOCKS.register("dusted_bedrock", () -> new DustedBedrockBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<Block> BEDROCK_GRAVEL = BLOCKS.register("bedrock_gravel", () -> new GravelBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL)));

    public static final RegistryObject<LiquidBlock> DUSTED_WATER = BLOCKS.register("dusted_water_block", () -> new LiquidBlock(ModFluids.SOURCE_DUSTED_WATER, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> MATH_ENERGY = BLOCKS.register("math_energy_block", () -> new LiquidBlock(ModFluids.SOURCE_MATH_ENERGY, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<Block> ADDER = BLOCKS.register("adder", () -> new AdderBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> NEGATOR = BLOCKS.register("negator", () -> new NegatorBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> ETCHING_MACHINE_DECODER = BLOCKS.register("etching_machine_decoder", () -> new EtchingMachineDecoderBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> ETCHING_MACHINE_ENCODER = BLOCKS.register("etching_machine_encoder",
            () -> new EtchingMachineEncoderBlock(BlockBehaviour.Properties.of(Material.METAL)));

    public static final RegistryObject<Block> ETCHING_MACHINE_CONTROLLER = BLOCKS.register("etching_machine_controller",
            () -> new EtchingMachineControllerBlock(BlockBehaviour.Properties.of(Material.METAL)));

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
