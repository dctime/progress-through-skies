package net.dctime.progressthroughskies.network.packets;

import net.dctime.progressthroughskies.registers.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerLocalScreamPacket
{

    public PlayerLocalScreamPacket()
    {
    }

    public PlayerLocalScreamPacket(FriendlyByteBuf buffer)
    {
    }

    public void encode(FriendlyByteBuf buffer)
    {
    }

    public void handling(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            // Make sure its on the PHYSICAL client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PlayerLocalScreamPacket.handlePacket(context));
        });

        context.get().setPacketHandled(true);
    }

    public static void handlePacket(Supplier<NetworkEvent.Context> context)
    {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null)
        {
            player.playSound(ModSoundEvents.DUSTED_WATER_HURT_EYE.get());
        }
    }
}
