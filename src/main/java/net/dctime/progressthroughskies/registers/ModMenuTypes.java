package net.dctime.progressthroughskies.registers;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.menus.AdderMenu;
import net.dctime.progressthroughskies.registers.menus.NegatorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ProgressThroughSkies.MODID);

    public static final RegistryObject<MenuType<AdderMenu>> ADDER_MENU_TYPE =
            MENU_TYPES.register("adder_menu_type", () -> IForgeMenuType.create(AdderMenu::new));
    public static final RegistryObject<MenuType<NegatorMenu>> NEGATOR_MENU_TYPE =
            MENU_TYPES.register("negator_menu_type", () -> IForgeMenuType.create(NegatorMenu::new));

    public static void register(IEventBus eventbus)
    {
        MENU_TYPES.register(eventbus);
    }
}
