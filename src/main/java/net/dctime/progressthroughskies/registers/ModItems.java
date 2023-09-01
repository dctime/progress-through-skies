package net.dctime.progressthroughskies.registers;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.items.RainWandItem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class ModItems
{
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProgressThroughSkies.MODID);

    public static final RegistryObject<Item> BEDROCK_DUST = ITEMS.register("bedrock_dust", () -> new Item(new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));
    public static final RegistryObject<BlockItem> DUSTED_BEDROCK = ITEMS.register("dusted_bedrock", () ->
            new BlockItem(ModBlocks.DUSTED_BEDROCK.get(), new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));
    public static final RegistryObject<BlockItem> BEDROCK_GRAVEL = ITEMS.register("bedrock_gravel", () ->
            new BlockItem(ModBlocks.BEDROCK_GRAVEL.get(), new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));
    public static final RegistryObject<Item> SAND_DUST = ITEMS.register("sand_dust", () -> new Item(new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));
    public static final RegistryObject<Item> GRAVEL_DUST = ITEMS.register("gravel_dust", () -> new Item(new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));
    public static final RegistryObject<Item> RAIN_WAND = ITEMS.register("rain_wand", () -> new RainWandItem(
            new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB).stacksTo(1).rarity(Rarity.UNCOMMON)
    ));

    public static final RegistryObject<Item> DUSTED_WATER_BUCKET = ITEMS.register("dusted_water_bucket",
            () -> new BucketItem(ModFluids.SOURCE_DUSTED_WATER, new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MATH_ENERGY_BUCKET = ITEMS.register("math_energy_bucket",
            () -> new BucketItem(ModFluids.SOURCE_MATH_ENERGY, new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB).craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<BlockItem> ADDER_ITEM = ITEMS.register("adder",
            () -> new BlockItem(ModBlocks.ADDER.get(), new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));
    public static final RegistryObject<BlockItem> NEGATOR_ITEM = ITEMS.register("negator",
            () -> new BlockItem(ModBlocks.NEGATOR.get(), new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));
    public static final RegistryObject<BlockItem> ETCHING_MACHINE_ITEM = ITEMS.register("etching_machine",
            () -> new BlockItem(ModBlocks.ETCHING_MACHINE.get(), new Item.Properties().tab(ModCreativeModTab.MOD_CREATIVE_MODE_TAB)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
