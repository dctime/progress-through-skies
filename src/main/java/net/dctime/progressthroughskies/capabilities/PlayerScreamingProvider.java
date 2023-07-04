package net.dctime.progressthroughskies.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.level.NoteBlockEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerScreamingProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
    public static Capability<PlayerScreaming> PLAYER_SCREAMING = CapabilityManager.get(new CapabilityToken<PlayerScreaming>() {
    });

    private PlayerScreaming scream = null;
    private final LazyOptional<PlayerScreaming> optional = LazyOptional.of(this::createPlayerScreaming);

    private PlayerScreaming createPlayerScreaming()
    {
        if (this.scream == null)
        {
            this.scream = new PlayerScreaming();
        }

        return this.scream;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_SCREAMING)
        {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerScreaming().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerScreaming().loadNBTData(nbt);
    }
}
