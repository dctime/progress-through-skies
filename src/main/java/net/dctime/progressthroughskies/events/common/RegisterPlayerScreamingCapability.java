package net.dctime.progressthroughskies.events.common;

import net.dctime.progressthroughskies.capabilities.PlayerScreaming;
import net.dctime.progressthroughskies.capabilities.PlayerScreamingProvider;
import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ProgressThroughSkies.MODID)
public class RegisterPlayerScreamingCapability
{
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        // if player doesn't have a capability, give him
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerScreamingProvider.PLAYER_SCREAMING).isPresent()) {
                event.addCapability(new ResourceLocation(ProgressThroughSkies.MODID, "properties"), new PlayerScreamingProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            // old store is the player's capability before dying
            // new store is the player's capability after respawn
            event.getOriginal().getCapability(PlayerScreamingProvider.PLAYER_SCREAMING).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerScreamingProvider.PLAYER_SCREAMING).ifPresent(newStore -> {
                    // put the old store's capability into new store's
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerScreaming.class);
    }
}
