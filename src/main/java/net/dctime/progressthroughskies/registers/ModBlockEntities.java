package net.dctime.progressthroughskies.registers;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.blockentities.AdderBlockEntity;
import net.dctime.progressthroughskies.registers.blockentities.EtchingMachineDecoderBlockEntity;
import net.dctime.progressthroughskies.registers.blockentities.EtchingMachineEncoderBlockEntity;
import net.dctime.progressthroughskies.registers.blockentities.NegatorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities
{
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ProgressThroughSkies.MODID);

    public static final RegistryObject<BlockEntityType<AdderBlockEntity>> ADDER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("adder_block_entity",
                    () -> BlockEntityType.Builder.of(AdderBlockEntity::new, ModBlocks.ADDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<NegatorBlockEntity>> NEGATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("negator_block_entity",
                    () -> BlockEntityType.Builder.of(NegatorBlockEntity::new, ModBlocks.NEGATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<EtchingMachineEncoderBlockEntity>> ETCHING_MACHINE_ENCODER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("etching_machine_encoder_block_entity",
                    () -> BlockEntityType.Builder.of(EtchingMachineEncoderBlockEntity::new, ModBlocks.ETCHING_MACHINE_ENCODER.get()).build(null));

    public static final RegistryObject<BlockEntityType<EtchingMachineDecoderBlockEntity>> ETCHING_MACHINE_DECODER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("etching_machine_decoder_block_entity",
                    () -> BlockEntityType.Builder.of(EtchingMachineDecoderBlockEntity::new, ModBlocks.ETCHING_MACHINE_DECODER.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
