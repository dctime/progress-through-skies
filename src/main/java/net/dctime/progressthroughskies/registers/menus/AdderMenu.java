package net.dctime.progressthroughskies.registers.menus;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.ModBlocks;
import net.dctime.progressthroughskies.registers.ModMenuTypes;
import net.dctime.progressthroughskies.registers.blockentities.AdderBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

// backend of screen
public class AdderMenu extends AbstractContainerMenu {
    private static final Logger LOGGER = LogUtils.getLogger();
    public final AdderBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public boolean isCrafting()
    {
        return data.get(0) > 0;
    }

    private FluidStack fluidStack;

    public int getScaledProgress()
    {
        int progress = data.get(0);
        int max_progress = data.get(1);
        int progress_arrow_size = 12;

//        LOGGER.debug("Processing" + progress_arrow_size * (progress / max_progress));
        return (int)(progress_arrow_size * ((float) progress / max_progress));
    }


    public AdderMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(5));
    }

    public AdderMenu(int id, Inventory inv, BlockEntity entity, ContainerData data)
    {
        super(ModMenuTypes.ADDER_MENU_TYPE.get(), id);
        checkContainerSize(inv, 5);
        blockEntity = (AdderBlockEntity) entity;
        this.level = inv.player.level;
        this.data = data;
        this.fluidStack = blockEntity.getFluid();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // get the item handler capability and do stuff (addSlot)
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 37, 38));
            this.addSlot(new SlotItemHandler(handler, 1, 77, 38));
            this.addSlot(new SlotItemHandler(handler, 2, 121, 38));
            this.addSlot(new SlotItemHandler(handler, 3, 112, 60));
            this.addSlot(new SlotItemHandler(handler, 4, 130, 60));
        });

        this.addDataSlots(data);
    }


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 5;  // must be the number of slots you have!

    // SHIFT CLICKING
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    public void setFluid(FluidStack fluidstack)
    {
//        LOGGER.debug("Packet arrived at menu");
        this.fluidStack = fluidstack;
    }

    public FluidStack getFluidStack()
    {
        return this.fluidStack;
    }

    // Can we open the block?
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, ModBlocks.ADDER.get());
    }

    private void addPlayerInventory(Inventory playerInventory)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int l = 0; l < 9; ++l)
            {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory)
    {
        for (int i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }


}
