package net.dctime.progressthroughskies.datagen.server;

import net.dctime.progressthroughskies.registers.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.BEDROCK_GRAVEL.get(), 1)
                .pattern("XX").pattern("XX").define('X', ModItems.BEDROCK_DUST.get())
                .unlockedBy("has_bedrock_dust", has(ModItems.BEDROCK_DUST.get())).save(consumer);
    }
}
