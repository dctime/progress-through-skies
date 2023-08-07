package net.dctime.progressthroughskies.registers;

import com.ibm.icu.impl.StringRange;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.recipes.AdderRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.windows.SECURITY_ATTRIBUTES;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProgressThroughSkies.MODID);

    public static final RegistryObject<RecipeSerializer<AdderRecipe>> ADDER_RECIPE =
            SERIALIZERS.register("adder", () -> AdderRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
    }
}
