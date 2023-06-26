package net.dctime.progressthroughskies.datagen.server;

import net.dctime.progressthroughskies.registers.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.block.Blocks;
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

        ShapedRecipeBuilder.shaped(Blocks.SAND, 1)
                .pattern("XX").pattern("XX").define('X', ModItems.SAND_DUST.get())
                .unlockedBy("has_sand_dust", has(ModItems.SAND_DUST.get())).save(consumer);

        ShapedRecipeBuilder.shaped(Blocks.GRAVEL, 1)
                .pattern("XX").pattern("XX").define('X', ModItems.GRAVEL_DUST.get())
                .unlockedBy("has_gravel_dust", has(ModItems.GRAVEL_DUST.get())).save(consumer);
    }
}
