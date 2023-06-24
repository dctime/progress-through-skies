package net.dctime.progressthroughskies.registers;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab
{
    public static final CreativeModeTab MOD_CREATIVE_MODE_TAB = new CreativeModeTab("progress_through_skies") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BEDROCK_DUST.get());
        }
    };
}
