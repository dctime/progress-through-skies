package net.dctime.progressthroughskies.lib;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/*
 * WrappedHandler by noeppi_noeppi changed by DCtime
 * under https://github.com/ModdingX/LibX/blob/1.19/LICENSE
 *
 */
public class FluidWrappedHandler implements IFluidHandler, IFluidTank {

    private static final Logger LOGGER = LogUtils.getLogger();
    private final FluidTank tank;
    private final boolean extract;
    private final Predicate<FluidStack> insert;

    public FluidWrappedHandler(FluidTank tank, boolean extract,
                               Predicate<FluidStack> insert) {
        this.tank = tank;
        this.extract = extract;
        this.insert = insert;
    }

    @Override
    public int getTanks() {
        return this.tank.getTanks();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return this.tank.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return this.tank.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return this.isFluidValid(stack);
    }

    @Override
    public @NotNull FluidStack getFluid() {
        return this.tank.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return this.tank.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return this.tank.getCapacity();
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return this.insert.test(stack) && this.tank.isFluidValid(stack);
    }

    public int fill(FluidStack resource, FluidAction action)
    {
//        LOGGER.debug("Filling fluids:" + resource.getFluid().toString());
        if (!this.isFluidValid(resource))
        {
//            LOGGER.debug("DENYYYY");
            return 0;
        }
//        LOGGER.debug("OKKKKK");
        return this.tank.fill(resource, action);
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        return this.extract ? this.tank.drain(resource, action) : FluidStack.EMPTY;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        return this.extract ? this.tank.drain(maxDrain, action) : FluidStack.EMPTY;
    }
}
