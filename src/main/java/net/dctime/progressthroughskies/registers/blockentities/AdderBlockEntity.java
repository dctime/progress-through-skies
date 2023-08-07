package net.dctime.progressthroughskies.registers.blockentities;

import com.ibm.icu.impl.StringRange;
import com.mojang.serialization.Decoder;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.menus.AdderMenu;
import net.dctime.progressthroughskies.registers.recipes.AdderRecipe;
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

import java.util.Optional;
import java.util.Random;

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
        return Component.translatable("guititle.progressthroughskies.adder");
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

        // inventory to store the current items in the item slots in the machine
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());

        for (int i = 0; i < 5; i++)
        {
            inventory.setItem(i, entity.itemStackHandler.getStackInSlot(i));
        }

        Optional<AdderRecipe> recipe = level.getRecipeManager().getRecipeFor(AdderRecipe.Type.INSTANCE, inventory, level);

        if (recipe.isPresent()) // start the crafting progress
        {
            entity.progress++;
            // set block entity
            setChanged(level, blockPos, blockState);

            if (entity.progress >= entity.maxProgress)
            {
                craftItem(entity, recipe.get()); //eat input get output
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

    private static void craftItem(AdderBlockEntity entity, AdderRecipe recipe) {
        Random rand = new Random();
        entity.itemStackHandler.extractItem(0, recipe.input_left.getCount(), false);
        entity.itemStackHandler.extractItem(1, recipe.input_right.getCount(), false);
        entity.itemStackHandler.setStackInSlot(2, new ItemStack(recipe.main_output.getItem(), entity.itemStackHandler.getStackInSlot(2).getCount()+recipe.main_output.getCount()));

        if (rand.nextDouble() < recipe.by_product_1_chance)
        {
            entity.itemStackHandler.setStackInSlot(3, new ItemStack(recipe.by_product_1.getItem(), entity.itemStackHandler.getStackInSlot(3).getCount()+recipe.by_product_1.getCount()));
        }

        if (rand.nextDouble() < recipe.by_product_2_chance)
        {
            entity.itemStackHandler.setStackInSlot(4, new ItemStack(recipe.by_product_2.getItem(), entity.itemStackHandler.getStackInSlot(4).getCount()+recipe.by_product_2.getCount()));
        }

        entity.resetProgress();
    }


//    private static boolean hasRecipe(AdderBlockEntity entity)
//    {
//        Level level = entity.level;
//
//        // inventory to store the current items in the item slots in the machine
//        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());
//
//        for (int i = 0; i < 5; i++)
//        {
//            inventory.setItem(i, entity.itemStackHandler.getStackInSlot(i));
//        }
//
//        assert level != null;
//        Optional<AdderRecipe> recipe = level.getRecipeManager().getRecipeFor(AdderRecipe.Type.INSTANCE, inventory, level);
//
//        if (recipe.isPresent())
//        {
//            boolean doOutputHasSpaceForOneMore = entity.itemStackHandler.getSlotLimit(2) >= entity.itemStackHandler.getStackInSlot(2).getCount()+recipe.get().main_output.getCount();
//            boolean doOutputSameAsInTheOutputSlot = entity.checkItemSame(recipe.get().main_output) || entity.itemStackHandler.getStackInSlot(2).isEmpty();
//            return doOutputHasSpaceForOneMore && doOutputSameAsInTheOutputSlot;
//        }
//        else
//        {
//            return false;
//        }
//
//
//    }

    private boolean checkItemSame(ItemStack itemStack)
    {
        return this.itemStackHandler.getStackInSlot(2).getItem() == itemStack.getItem();
    }
}















