package net.dctime.progressthroughskies.registers;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.blocks.DustedBedrockBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GravelBlock;
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

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
