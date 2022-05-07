package com.octenexin.inifield.client;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.init.ModBlocks;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class LoadingScreenListener {

    private final Minecraft client = Minecraft.getInstance();

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {

        IniField.LOGGER.debug("onOpenGui!");
        if (event.getGui() instanceof WorldLoadProgressScreen && client.player != null) {
            RegistryKey<World> tfDimension = RegistryKey.create(Registry.DIMENSION_REGISTRY, IniField.locate("aether"));
//            if (client.player.clientLevel.getBlockState(client.player.blockPosition().below()).getBlock() == ModBlocks.AETHER_PORTAL.get() || client.player.clientLevel.dimension() == tfDimension) {
//
//            }
            IniField.LOGGER.debug("onOpenStartGui!");
            LoadingScreenGui guiLoading = new LoadingScreenGui();
            guiLoading.setEntering(client.player.clientLevel.dimension() == World.OVERWORLD);
            event.setGui(guiLoading);
        }
    }
}
