package net.dctime.progressthroughskies.registers;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids
{
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, ProgressThroughSkies.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_DUSTED_WATER = FLUIDS.register("dusted_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.DUSTED_WATER_PROPERTIES)
    );

    public static final RegistryObject<FlowingFluid> FLOWING_DUSTED_WATER = FLUIDS.register("flowing_dusted_water_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.DUSTED_WATER_PROPERTIES)
    );

    private static final ForgeFlowingFluid.Properties DUSTED_WATER_PROPERTIES =
            new ForgeFlowingFluid.Properties(ModFluidTypes.DUSTED_WATER, SOURCE_DUSTED_WATER, FLOWING_DUSTED_WATER)
                    .explosionResistance(100f).levelDecreasePerBlock(1).slopeFindDistance(1)
                    .block(ModBlocks.DUSTED_WATER).bucket(ModItems.DUSTED_WATER_BUCKET);


    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }

}
