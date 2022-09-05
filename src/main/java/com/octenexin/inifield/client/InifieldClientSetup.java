package com.octenexin.inifield.client;


import com.octenexin.inifield.init.ModEntityRenderers;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.dimension.AetherSkyProperty;
import com.octenexin.inifield.world.dimension.ExtremSkyProperty;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = Reference.MOD_ID)
public class InifieldClientSetup {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
//        try {
//            Class.forName("net.optifine.Config");
//            optifinePresent = true;
//        } catch (ClassNotFoundException e) {
//            optifinePresent = false;
//        }
//        TFItems.addItemModelProperties();

//        KnightmetalArmorItem.initArmorModel();
//        PhantomArmorItem.initArmorModel();
//        YetiArmorItem.initArmorModel();
//        ArcticArmorItem.initArmorModel();
//        FieryArmorItem.initArmorModel();
          MinecraftForge.EVENT_BUS.register(new LoadingScreenListener());
//        RenderLayerRegistration.init();
          RenderTypeRegistry.onRenderTypeSetup();
          ModEntityRenderers.registerEntityRenderers();
//        TFTileEntities.registerTileEntityRenders();
//        TFContainers.renderScreens();

          DimensionRenderInfo.EFFECTS.put(new ResourceLocation(Reference.MOD_ID, "aether_sky"), new AetherSkyProperty());
          DimensionRenderInfo.EFFECTS.put(new ResourceLocation(Reference.MOD_ID, "extrem_sky"), new ExtremSkyProperty());
//        TwilightForestRenderInfo renderInfo = new TwilightForestRenderInfo(128.0F, false, DimensionRenderInfo.FogType.NONE, false, false);
//        DimensionRenderInfo.field_239208_a_.put(TwilightForestMod.prefix("renderer"), renderInfo);
//
//        evt.enqueueWork(() -> {
//            Atlases.addWoodType(TFBlocks.TWILIGHT_OAK);
//            Atlases.addWoodType(TFBlocks.CANOPY);
//            Atlases.addWoodType(TFBlocks.MANGROVE);
//            Atlases.addWoodType(TFBlocks.DARKWOOD);
//            Atlases.addWoodType(TFBlocks.TIMEWOOD);
//            Atlases.addWoodType(TFBlocks.TRANSFORMATION);
//            Atlases.addWoodType(TFBlocks.MINING);
//            Atlases.addWoodType(TFBlocks.SORTING);
//        });

    }


}
