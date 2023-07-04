package net.dctime.progressthroughskies.datagen.client;

import net.dctime.progressthroughskies.events.mod.ProgressThroughSkies;
import net.dctime.progressthroughskies.registers.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider
{

    /**
     * Creates a new instance of this data provider.
     *
     * @param generator The data generator instance provided by the event you are initializing this provider in.
     * @param helper    The existing file helper provided by the event you are initializing this provider in.
     */
    public ModSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, ProgressThroughSkies.MODID, helper);
    }

    @Override
    public void registerSounds()
    {
        this.add(
                ModSoundEvents.DUSTED_WATER_HURT_EYE,
                definition().subtitle("sound.progressthroughskies.dusted_water_hurt_eye")
                        .with(
                                sound(ModSoundEvents.DUSTED_WATER_HURT_EYE.get().getLocation(), SoundDefinition.SoundType.SOUND)
                        )
        );
    }
}
