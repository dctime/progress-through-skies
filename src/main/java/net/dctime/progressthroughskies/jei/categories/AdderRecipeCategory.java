package net.dctime.progressthroughskies.jei.categories;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.jei.JEIProgressThroughSkiesPlugin;
import net.dctime.progressthroughskies.registers.ModItems;
import net.dctime.progressthroughskies.registers.recipes.AdderRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AdderRecipeCategory implements IRecipeCategory<AdderRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(ProgressThroughSkies.MODID, "adder");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ProgressThroughSkies.MODID, "textures/gui/adder_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AdderRecipeCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.ADDER_ITEM.get()));
    }

    @Override
    public RecipeType<AdderRecipe> getRecipeType() {
        return JEIProgressThroughSkiesPlugin.ADD_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jeititle.progressthroughskies.recipetype.adder");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AdderRecipe recipe, IFocusGroup focuses) {
        String chance = Component.translatable("word.progressthroughskies.chance").getString();
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 38).addItemStack(recipe.input_left);
        builder.addSlot(RecipeIngredientRole.INPUT, 77, 38).addItemStack(recipe.input_right);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 38).addItemStack(recipe.main_output);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 60).addItemStack(recipe.by_product_1).addTooltipCallback(
                (recipeSlotView, tooltip) -> tooltip.add(Component.literal(chance + ": " + recipe.by_product_1_chance * 100 + "%")));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 60).addItemStack(recipe.by_product_2).addTooltipCallback(
                (recipeSlotView, tooltip) -> tooltip.add(Component.literal(chance + ": " + recipe.by_product_2_chance * 100 + "%")));

    }
}
