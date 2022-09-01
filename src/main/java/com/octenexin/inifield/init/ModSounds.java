package com.octenexin.inifield.init;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);
    public static RegistryObject<SoundEvent> MEA_SOUND = SOUNDS.register("mea", () -> {
        return new SoundEvent(IniField.locate("mea"));
    });
}
