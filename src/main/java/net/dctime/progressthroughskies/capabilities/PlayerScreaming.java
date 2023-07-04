package net.dctime.progressthroughskies.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerScreaming
{
    private int screamingCooldown = 0;
    private final int START_SCREAMING = 400;

    public int getScreamingCooldown()
    {
        return screamingCooldown;
    }

    public boolean isScreaming()
    {
        return screamingCooldown > 0;
    }

    public void setScreamingCooldown(int screamingCooldown)
    {
        if (screamingCooldown < 0)
        {
            this.screamingCooldown = 0;
        }
        else if (screamingCooldown > START_SCREAMING)
        {
            this.screamingCooldown = START_SCREAMING;
        }
        else
        {
            this.screamingCooldown = screamingCooldown;
        }
    }

    public void startScreaming()
    {
        // the audio is 280 ticks long
        this.screamingCooldown = 280;
    }

    public void tick()
    {
        setScreamingCooldown(this.screamingCooldown -= 1);
    }
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putInt("screaming_cooldown", screamingCooldown);
    }

    public void loadNBTData(CompoundTag nbt)
    {
        screamingCooldown = nbt.getInt("screaming_cooldown");
    }

    public void copyFrom(PlayerScreaming source) {
        this.screamingCooldown = source.screamingCooldown;
    }
}
