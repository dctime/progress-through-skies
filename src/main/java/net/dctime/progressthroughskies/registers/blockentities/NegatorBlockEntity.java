package net.dctime.progressthroughskies.registers.blockentities;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.ModBlockEntities;
import net.dctime.progressthroughskies.registers.menus.NegatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class NegatorBlockEntity extends BlockEntity implements MenuProvider
{
    private static final Logger LOGGER = LogUtils.getLogger();
    protected final ContainerData data;

    public NegatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.NEGATOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        // data communicates the menu
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("guititle.progressthroughskies.negator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new NegatorMenu(pContainerId, pPlayerInventory, this, this.data);
    }
}
