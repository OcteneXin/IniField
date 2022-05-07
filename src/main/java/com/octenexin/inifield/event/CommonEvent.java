package com.octenexin.inifield.event;


import com.octenexin.inifield.IniField;
import com.octenexin.inifield.client.gui.SimpleDialog;
import com.octenexin.inifield.client.particles.WeedFurryParticleData;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.dimension.AetherTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EditBookScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

import static com.octenexin.inifield.utils.ModGeneralUtils.attemptSendPlayer;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CommonEvent {


    @SubscribeEvent
    public static void onFallDownAether(TickEvent.PlayerTickEvent event){
        PlayerEntity playerEntity=event.player;
        World world=playerEntity.getCommandSenderWorld();
        RegistryKey<World> aether = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Reference.MOD_ID,"aether"));



        if((world instanceof ServerWorld)&&playerEntity.level.dimension().location().equals(aether.location())){
            if(playerEntity.position().y<=0){
                IniField.LOGGER.debug(playerEntity.level.isClientSide());
                IniField.LOGGER.debug(playerEntity.level.dimension().location());
                attemptSendPlayer(playerEntity,true);
            }
        }

    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void rightClickStick(PlayerInteractEvent.RightClickBlock event){

        PlayerEntity entity=event.getPlayer();
        World world=event.getWorld();
        IniField.LOGGER.info("clicks stick");

        if(event.getHand()!= Hand.MAIN_HAND||event.getItemStack().getItem()!= Items.STICK){
            return;
        }

        if(world instanceof ServerWorld){
            IniField.LOGGER.info("clicks stick 3");
            BlockPos pos=event.getPos();

            world.addParticle(new WeedFurryParticleData(255,255,255),pos.getX(),pos.getY()+1,pos.getZ(),0D,0.1D,0D);

            IniField.LOGGER.info("clicks stick success!");

        }
    }


    @SubscribeEvent
    public static void rightClickDiamond(PlayerInteractEvent.RightClickBlock event){

        PlayerEntity entity=event.getPlayer();
        World world=event.getWorld();
        IniField.LOGGER.info("clicks1");

        if(event.getHand()!= Hand.MAIN_HAND||event.getItemStack().getItem()!= Items.DIAMOND){
            return;
        }
        IniField.LOGGER.info("clicks2");
        if(world instanceof ServerWorld){
            IniField.LOGGER.info("clicks3");
            //RegistryKey<World> registrykey = world.dimension() == World.END ? World.OVERWORLD : World.END;
            //attemptSendPlayer(entity,true);
            Minecraft minecraft=Minecraft.getInstance();

            minecraft.setScreen(new SimpleDialog("small_dialog.png","inifield.look.overworld.grass","inifield.test.pl_choice0","inifield.test.pl_choice1",30));
        }
    }


}
