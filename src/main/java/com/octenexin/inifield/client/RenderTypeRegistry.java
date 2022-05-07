package com.octenexin.inifield.client;


import com.octenexin.inifield.init.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


public class RenderTypeRegistry {

    public static void onRenderTypeSetup() {
        RenderTypeLookup.setRenderLayer(ModBlocks.LIGHT_CLOUD.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.LIGHT_RAINING_CLOUD.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.WEED_GRASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SNOW_DROP_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.AETHER_PORTAL.get(), RenderType.translucent());
    }
}
