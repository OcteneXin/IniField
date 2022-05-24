package com.octenexin.inifield.event;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.init.ModBiomes;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetupEvent {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        IniField.LOGGER.debug("on common setup!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(ModBiomes.COUNTRYSIDE_WHEAT, 1000));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(ModBiomes.CITY, 1000));

    }
}