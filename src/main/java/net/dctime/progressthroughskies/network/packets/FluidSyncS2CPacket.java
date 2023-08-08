package net.dctime.progressthroughskies.network.packets;

import com.mojang.logging.LogUtils;
import net.dctime.progressthroughskies.registers.blockentities.AdderBlockEntity;
import net.dctime.progressthroughskies.registers.menus.AdderMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class FluidSyncS2CPacket
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private FluidStack FLUIDSTACK;
    private BlockPos BLOCK_POS;

    public FluidSyncS2CPacket(FluidStack fluidStack, BlockPos pos)
    {
        this.FLUIDSTACK = fluidStack;
        this.BLOCK_POS = pos;
    }

    public FluidSyncS2CPacket(FriendlyByteBuf buf)
    {
        this.FLUIDSTACK = buf.readFluidStack();
        this.BLOCK_POS = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeFluidStack(FLUIDSTACK);
        buf.writeBlockPos(BLOCK_POS);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
           if (Minecraft.getInstance().level.getBlockEntity(this.BLOCK_POS) instanceof AdderBlockEntity adderBlockEntity)
           {
//               LOGGER.debug("packet sending to blockentity");
               adderBlockEntity.setFluid(this.FLUIDSTACK);

               if (Minecraft.getInstance().player.containerMenu instanceof AdderMenu adderMenu &&
               adderMenu.blockEntity.getBlockPos().equals(BLOCK_POS))
               {
//                   LOGGER.debug("packet sending to menu");
                    adderMenu.setFluid(this.FLUIDSTACK);
               }
           }
        });

        supplier.get().setPacketHandled(true);
    }
}
