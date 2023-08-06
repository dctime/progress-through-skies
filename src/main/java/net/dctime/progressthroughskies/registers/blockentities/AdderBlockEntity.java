package net.dctime.progressthroughskies.registers.blockentities;

import com.mojang.serialization.Decoder;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.menus.AdderMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdderBlockEntity extends BlockEntity implements MenuProvider
{
    // For syncing with menu in client
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public AdderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ADDER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex)
                {
                    case 0 -> AdderBlockEntity.this.progress;
                    case 1 -> AdderBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex)
                {
                    case 0 -> AdderBlockEntity.this.progress = pValue;
                    case 1 -> AdderBlockEntity.this.maxProgress = pValue;
                }

            }

            @Override
            public int getCount() {
                // two index 0 and 1
                return 2;
            }
        };
    }

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(5)
    {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            super.onContentsChanged(slot);
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemStackHandler = LazyOptional.empty();


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return lazyItemStackHandler.cast();
        }
        return super.getCapability(cap, side);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("progressthroughkies.adder.gui.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AdderMenu(pContainerId, pPlayerInventory, this, this.data);
    }



    @Override
    public void onLoad() {
        lazyItemStackHandler = LazyOptional.of(() -> itemStackHandler);
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemStackHandler.invalidate();
    }

    // When player leaving the world, put items into file (nbt data)
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    // When chunk is loaded or player joins the world
    @Override
    public void load(CompoundTag pTag) {
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        super.load(pTag);
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    // called in every tick
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, AdderBlockEntity entity)
    {
        // entity == this
        if(level.isClientSide())
        {
            return;
        }

        if (hasRecipe(entity)) // start the crafting progress
        {
            entity.progress++;
            // set block entity
            setChanged(level, blockPos, blockState);

            if (entity.progress >= entity.maxProgress)
            {
                craftItem(entity); //eat input get output
                entity.progress = 0;
            }

        }
        else
        {
            entity.resetProgress();
            setChanged(level, blockPos, blockState);
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(AdderBlockEntity entity) {
        if (hasRecipe(entity))
        {
            entity.itemStackHandler.extractItem(0, 1, false);
            entity.itemStackHandler.setStackInSlot(2, new ItemStack(Items.DIRT, entity.itemStackHandler.getStackInSlot(2).getCount()+1));
        }

        entity.resetProgress();
    }

    private static boolean hasRecipe(AdderBlockEntity entity)
    {
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());

        for (int i = 0; i < 5; i++)
        {
            inventory.setItem(i, entity.itemStackHandler.getStackInSlot(i));
        }

        boolean leftValidItem = entity.itemStackHandler.getStackInSlot(0).getItem() == Items.DIRT;
        boolean doOutputHasSpaceForOneMore = entity.itemStackHandler.getSlotLimit(2) > entity.itemStackHandler.getStackInSlot(2).getCount() ;
        boolean doOutputSameAsInTheOutputSlot = entity.checkItemSame(Items.DIRT.getDefaultInstance()) || entity.itemStackHandler.getStackInSlot(2).isEmpty();

        return leftValidItem && doOutputHasSpaceForOneMore && doOutputSameAsInTheOutputSlot;
    }

    private boolean checkItemSame(ItemStack itemStack)
    {
        return this.itemStackHandler.getStackInSlot(2).getItem() == itemStack.getItem();
    }
}















