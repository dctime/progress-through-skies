package net.dctime.progressthroughskies.registers.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ibm.icu.impl.StringRange;
import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.lib.RecipeUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class AdderRecipe implements Recipe<SimpleContainer>
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public final ResourceLocation id;
    // recipe output
    public final ItemStack input_left;
    public final ItemStack input_right;
    public final ItemStack main_output;
    public final ItemStack by_product_1;
    public final ItemStack by_product_2;
    public final double by_product_1_chance;
    public final double by_product_2_chance;
    public final FluidStack fluid_output;

    public AdderRecipe(ResourceLocation id, ItemStack input_left, ItemStack input_right, ItemStack main_output, ItemStack by_product_1, ItemStack by_product_2,
                       double by_product_1_chance, double by_produce_2_chance, FluidStack fluid_output)
    {
        this.id = id;
        this.input_left = input_left;
        this.input_right = input_right;
        this.main_output = main_output;
        this.by_product_1 = by_product_1;
        this.by_product_2 = by_product_2;
        this.by_product_1_chance = by_product_1_chance;
        this.by_product_2_chance = by_produce_2_chance;
        this.fluid_output = fluid_output;
    }

    // If the items in the block matches the recipe
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()){
            // Its the servers job
            return false;
        }

        boolean doOutputHasSpaceForOneMore = pContainer.getItem(2).getMaxStackSize() >= pContainer.getItem(2).getCount()+main_output.getCount();
        boolean doOutputSameAsInTheOutputSlot = pContainer.getItem(2).getItem() == main_output.getItem() || pContainer.getItem(2).isEmpty();
        return checkSameAndMore(pContainer.getItem(0), input_left) && checkSameAndMore(pContainer.getItem(1), input_right) && doOutputHasSpaceForOneMore && doOutputSameAsInTheOutputSlot;
    }

    private boolean checkSameAndMore(ItemStack targetItem, ItemStack recipeItem)
    {
        boolean sameItem = targetItem.is(recipeItem.getItem());
        boolean moreCount = targetItem.getCount() >= recipeItem.getCount();

        return sameItem && moreCount;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return main_output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return main_output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AdderRecipe>
    {
        private Type()
        {

        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "adder";
    }

    public static class Serializer implements RecipeSerializer<AdderRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ProgressThroughSkies.MODID, "adder");

        @Override
        public @NotNull AdderRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack input_left = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input_left"));
            ItemStack input_right = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input_right"));
            ItemStack main_output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "main_output"));
            ItemStack by_product_1 = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "by_product_1"));
            ItemStack by_product_2 = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "by_product_2"));
            double by_product_1_chance = GsonHelper.getAsDouble(pSerializedRecipe, "by_product_1_chance");
            double by_product_2_chance = GsonHelper.getAsDouble(pSerializedRecipe, "by_product_2_chance");
            JsonObject fluid_output_object = pSerializedRecipe.get("fluid_output").getAsJsonObject();
            FluidStack fluid_output = RecipeUtil.getFluid(fluid_output_object);

            return new AdderRecipe(pRecipeId, input_left, input_right, main_output, by_product_1, by_product_2, by_product_1_chance, by_product_2_chance, fluid_output);
        }


        // Get recipes from network Server - Client both ways
        @Override
        public @Nullable AdderRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack input_left = pBuffer.readItem();
            ItemStack input_right = pBuffer.readItem();
            ItemStack main_output = pBuffer.readItem();
            ItemStack by_product_1 = pBuffer.readItem();
            ItemStack by_product_2 = pBuffer.readItem();
            double by_product_1_chance = pBuffer.readDouble();
            double by_product_2_chance = pBuffer.readDouble();
            FluidStack fluid_output = pBuffer.readFluidStack();
            return new AdderRecipe(pRecipeId, input_left, input_right, main_output, by_product_1, by_product_2, by_product_1_chance, by_product_2_chance, fluid_output);
        }

        // Mush match the fromNetwork
        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AdderRecipe pRecipe) {
            pBuffer.writeItemStack(pRecipe.input_left, false);
            pBuffer.writeItemStack(pRecipe.input_right, false);
            pBuffer.writeItemStack(pRecipe.main_output, false);
            pBuffer.writeItemStack(pRecipe.by_product_1, false);
            pBuffer.writeItemStack(pRecipe.by_product_2, false);
            pBuffer.writeDouble(pRecipe.by_product_1_chance);
            pBuffer.writeDouble(pRecipe.by_product_2_chance);
            pBuffer.writeFluidStack(pRecipe.fluid_output);

        }
    }

}
