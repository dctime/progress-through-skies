package net.dctime.progressthroughskies.datagen.server.loot_tables;

import net.dctime.progressthroughskies.registers.ModBlocks;
import net.dctime.progressthroughskies.registers.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTable extends BlockLoot
{
    @Override
    protected void addTables() {
        this.dropSelf(ModBlocks.DUSTED_BEDROCK.get());
        // CREDIT: https://github.com/teaconmc/Xiaozhong/blob/f0db8ee20a9686a7a301e07c39ccd9f2fb0657a0/docs/1.18.x/block-item-objects/README.mdx#L356
        /*
        // 如欲在非精准采集的情况下掉落九个 xiaozhong:sulfur_dust，请使用以下代码：
        this.add(SULFUR_BLOCK.get(), block -> createSingleItemTableWithSilkTouch(block, SULFUR_DUST_ITEM.get(), ConstantValue.exactly(9f)));
        */
        this.add(ModBlocks.BEDROCK_GRAVEL.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .setRolls(UniformGenerator.between(2, 4))
                    .add(LootItem.lootTableItem(ModItems.GRAVEL_DUST.get()))
        ).withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(ModItems.SAND_DUST.get()))));
        this.dropSelf(ModBlocks.ADDER.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().flatMap(RegistryObject::stream)::iterator;
    }
}
