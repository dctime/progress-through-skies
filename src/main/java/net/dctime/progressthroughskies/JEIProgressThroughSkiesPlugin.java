package net.dctime.progressthroughskies;

import com.ibm.icu.impl.StringRange;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModItems;
import net.dctime.progressthroughskies.registers.recipes.AdderRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIProgressThroughSkiesPlugin implements IModPlugin
{
    public static RecipeType<AdderRecipe> ADD_TYPE =
            new RecipeType<>(AdderRecipeCategory.UID, AdderRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ProgressThroughSkies.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AdderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<AdderRecipe> recipes_adding = rm.getAllRecipesFor(AdderRecipe.Type.INSTANCE);
        registration.addRecipes(ADD_TYPE, recipes_adding);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.ADDER_ITEM.get().getDefaultInstance(), ADD_TYPE);
    }
}
