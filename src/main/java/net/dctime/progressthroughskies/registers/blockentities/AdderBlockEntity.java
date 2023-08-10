package net.dctime.progressthroughskies.registers.blockentities;

import com.ibm.icu.impl.StringRange;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Decoder;
import net.dctime.progressthroughskies.lib.FluidWrappedHandler;
import net.dctime.progressthroughskies.lib.WrappedHandler;
import net.dctime.progressthroughskies.network.ModNetworkHandler;
import net.dctime.progressthroughskies.network.packets.FluidSyncS2CPacket;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.ModFluidTypes;
import net.dctime.progressthroughskies.registers.ModFluids;
import net.dctime.progressthroughskies.registers.blocks.AdderBlock;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class AdderBlockEntity extends BlockEntity implements MenuProvider
{
    private static final Logger LOGGER = LogUtils.getLogger();
    // For syncing with menu in client
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    private final FluidTank FLUID_TANK = new FluidTank(64000)
    {
        @Override
        protected void onContentsChanged()
        {
//            LOGGER.debug("Fluid change detect");
            setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                ModNetworkHandler.CHANNEL_INSTANCE.send(PacketDistributor.ALL.noArg(), new FluidSyncS2CPacket(this.fluid, worldPosition));
//                LOGGER.debug("Block Entity Packet Send: Fluid: " + this.fluid);
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public void setFluid(FluidStack stack)
    {
//        LOGGER.debug("Packet Arrived at blockEntity");
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluid()
    {
        return this.FLUID_TANK.getFluid();
    }

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

        // can stack put into slot? used in directionWrappedHandlerMap
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot)
            {
                case 0 -> true;
                case 1 -> true;
                case 2 -> false;
                case 3 -> false;
                case 4 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    // pass in direction (block's(relative), not someone who place's perspective (general)), get LazyOptional<WrappedHandler>
    // (i) -> i == 3 (Can extract item of 3rd slot)
    // (i, s) -> true s can be inserted i
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 3 || i == 4, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> false,
                            (index, stack) -> false)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> false, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> false,
                            (index, stack) -> index == 0 && itemStackHandler.isItemValid(0, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 2,
                            (index, stack) -> false)),
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> false,
                            (index, stack) -> index == 1 && itemStackHandler.isItemValid(1, stack))));

    private final Map<Direction, LazyOptional<FluidWrappedHandler>> directionFluidWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new FluidWrappedHandler(FLUID_TANK, true, (f) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new FluidWrappedHandler(FLUID_TANK, true, (f) -> false)),
                    Direction.SOUTH, LazyOptional.of(() -> new FluidWrappedHandler(FLUID_TANK, true, (f) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new FluidWrappedHandler(FLUID_TANK, true, (f) -> false)),
                    Direction.WEST, LazyOptional.of(() -> new FluidWrappedHandler(FLUID_TANK, true, (f) -> false)),
                    Direction.UP, LazyOptional.of(() -> new FluidWrappedHandler(FLUID_TANK, true, (f) -> false)));

    private LazyOptional<ItemStackHandler> lazyItemStackHandler = LazyOptional.empty();


    // if someone want to get capability of the block entity
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER)
        {
            if (side == null)
            {
                return lazyItemStackHandler.cast(); // return lazyItemStackHandler nothing changes
            }

            // limit Sides
            if (directionWrappedHandlerMap.containsKey(side))
            {
                Direction localDir = this.getBlockState().getValue(AdderBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN)
                {
                    return directionWrappedHandlerMap.get(side).cast();
                }
                return switch (localDir)
                {
                    // NORTH: PEOPLE at south when placed, NORTH SIDE IS AT SOUTH, NEED FLIP
                    // cast() return the value of the lambda expression stored in the directionWrappedHandling
                    case NORTH -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    // EAST:　PEOPLE at east, NORTH SIDE is at EAST, counterclockwise to match the local one to the people one
                    case EAST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    default -> directionWrappedHandlerMap.get(side).cast();
                };
            }

        }

        if (cap == ForgeCapabilities.FLUID_HANDLER)
        {
            if (side == null)
            {
                return lazyFluidHandler.cast();
            }
            if (directionFluidWrappedHandlerMap.containsKey(side))
            {
//                LOGGER.debug("Fluid Fill in request from sides");
                Direction localDir = this.getBlockState().getValue(AdderBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN)
                {
                    return directionFluidWrappedHandlerMap.get(side).cast();
                }
                return switch (localDir)
                {
                    // NORTH: PEOPLE at south when placed, NORTH SIDE IS AT SOUTH, NEED FLIP
                    // cast() return the value of the lambda expression stored in the directionWrappedHandling
                    case NORTH -> directionFluidWrappedHandlerMap.get(side.getOpposite()).cast();
                    case SOUTH -> directionFluidWrappedHandlerMap.get(side).cast();
                    // EAST:　PEOPLE at east, NORTH SIDE is at EAST, counterclockwise to match the local one to the people one
                    case EAST -> directionFluidWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                    case WEST -> directionFluidWrappedHandlerMap.get(side.getClockWise()).cast();
                    default -> directionFluidWrappedHandlerMap.get(side).cast();
                };

            }

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
        ModNetworkHandler.CHANNEL_INSTANCE.send(PacketDistributor.ALL.noArg(), new FluidSyncS2CPacket(this.getFluid(), worldPosition));
        return new AdderMenu(pContainerId, pPlayerInventory, this, this.data);
    }



    // Call when first added to the world
    @Override
    public void onLoad() {
        lazyItemStackHandler = LazyOptional.of(() -> itemStackHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
        super.onLoad();
    }

    // Check it its ok
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemStackHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    // When player leaving the world, put items into file (nbt data)
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(pTag);
        FLUID_TANK.writeToNBT(pTag);
    }

    // When chunk is loaded or player joins the world
    @Override
    public void load(CompoundTag pTag) {
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        FLUID_TANK.readFromNBT(pTag);
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
        if (recipe.isPresent() && (recipe.get().fluid_output.getFluid().isSame(entity.getFluid().getFluid()) || entity.FLUID_TANK.isEmpty())) // start the crafting progress
        {
            entity.maxProgress = recipe.get().duration;
            entity.progress++;
            // set block entity
            setChanged(level, blockPos, blockState);

            if (entity.progress >= entity.maxProgress)
            {
                craftItem(entity, recipe.get()); //eat input get output
//                LOGGER.debug("Fluid Tank changed in BlockEntity");
                entity.FLUID_TANK.fill(recipe.get().fluid_output, IFluidHandler.FluidAction.EXECUTE);
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















