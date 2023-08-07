package net.dctime.progressthroughskies.registers;

import com.mojang.math.Vector3f;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.fluids.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.http.conn.routing.RouteInfo;

public class ModFluidTypes
{
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ProgressThroughSkies.MODID);

    public static RegistryObject<FluidType> DUSTED_WATER =
            FLUID_TYPES.register("dusted_water", () -> new BaseFluidType(
                    new ResourceLocation("block/water_still"),
                    new ResourceLocation("block/water_flow"),
                    new ResourceLocation(ProgressThroughSkies.MODID, "misc/in_dusted_water"),
                    0xff451803,
                    new Vector3f(69f/255f, 24f/255f, 3f/255f),
                    FluidType.Properties.create().canDrown(true).canHydrate(true).density(15).lightLevel(1)
            ));

    public static RegistryObject<FluidType> MATH_ENERGY =
            FLUID_TYPES.register("math_energy", () -> new BaseFluidType(
                    new ResourceLocation("block/water_still"),
                    new ResourceLocation("block/water_flow"),
                    new ResourceLocation(ProgressThroughSkies.MODID, "misc/in_math_energy"),
                    0xff4fe5ff,
                    new Vector3f(79f/255f, 229f/255f, 254f/255f),
                    FluidType.Properties.create().canDrown(true).canHydrate(true).density(15).lightLevel(1)
            ));

    public static void register(IEventBus eventBus)
    {
        FLUID_TYPES.register(eventBus);
    }
}
