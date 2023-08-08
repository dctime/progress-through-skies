package net.dctime.progressthroughskies.lib;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidTagIngredient {

    private final FluidStack fluid;
    private final String tag;
    private final int amount; // copy of fluidstack count used for tags

    public FluidTagIngredient(FluidStack fs, String str, int cnt) {
        fluid = (fs == null) ? FluidStack.EMPTY : fs;
        tag = (str == null) ? "" : str;
        amount = cnt;
    }

    public FluidStack getFluidStack() {
        return fluid;
    }

    public String getTag() {
        return tag;
    }

    public boolean hasFluid() {
        return !fluid.isEmpty();
    }

    public boolean hasTag() {
        return !tag.isEmpty();
    }

    public List<Fluid> list() {
        if (!hasTag()) {
            return List.of(fluid.getFluid());
        }
        TagKey<Fluid> ft = FluidTags.create(new ResourceLocation(tag));
        if (ft != null) {
            TagKey<Fluid> key = ForgeRegistries.FLUIDS.tags().createTagKey(new ResourceLocation(tag));
            return ForgeRegistries.FLUIDS.tags().getTag(key).stream().toList();
        }
        return null;
    }

    /**
     * create fluidstacks for all fluids matching the tag. if hastag
     *
     * @return
     */
    public List<FluidStack> getMatchingFluids() {
        List<Fluid> fluids = list();
        List<FluidStack> me = new ArrayList<>();
        for (Fluid f : fluids) {
            me.add(new FluidStack(f, this.amount));
        }
        return me;
    }

    public static FluidTagIngredient readFromPacket(FriendlyByteBuf buffer) {
        return new FluidTagIngredient(FluidStack.readFromPacket(buffer), buffer.readUtf(), buffer.readInt());
    }

    public void writeToPacket(FriendlyByteBuf buffer) {
        fluid.writeToPacket(buffer);
        buffer.writeUtf(tag);
        buffer.writeInt(amount);
    }

    public int getAmount() {
        return amount;
    }
}
