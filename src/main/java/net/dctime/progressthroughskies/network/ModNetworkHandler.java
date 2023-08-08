package net.dctime.progressthroughskies.network;

import net.dctime.progressthroughskies.network.packets.FluidSyncS2CPacket;
import net.dctime.progressthroughskies.network.packets.PlayerLocalScreamPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int id = 0;
    public static final SimpleChannel CHANNEL_INSTANCE = NetworkRegistry.newSimpleChannel
    (
        new ResourceLocation("progressthroughskies", "network"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void init()
    {
        CHANNEL_INSTANCE.registerMessage
        (
            id++,
            PlayerLocalScreamPacket.class,
                PlayerLocalScreamPacket::encode,
                PlayerLocalScreamPacket::new,
                PlayerLocalScreamPacket::handling
        );

        CHANNEL_INSTANCE.registerMessage
        (
            id++,
            FluidSyncS2CPacket.class,
            FluidSyncS2CPacket::toBytes,
            FluidSyncS2CPacket::new,
            FluidSyncS2CPacket::handle
        );
    }
}
