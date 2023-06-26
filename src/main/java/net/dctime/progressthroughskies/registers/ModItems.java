package net.dctime.progressthroughskies.registers;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.items.RainWandItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
