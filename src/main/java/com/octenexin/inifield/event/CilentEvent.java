package com.octenexin.inifield.event;


import com.octenexin.inifield.IniField;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.dimension.AetherTeleporter;
import com.octenexin.inifield.world.dimension.DimensionLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CilentEvent {

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
            attemptSendPlayer(entity,true);
        }
    }

    private static RegistryKey<World> getDestination(Entity entity) {
        RegistryKey<World> aether = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Reference.MOD_ID,"aether"));

        return !entity.level.dimension().location().equals(aether.location())
                ? aether : World.OVERWORLD;
    }

    public static void attemptSendPlayer(Entity entity, boolean forcedEntry) {

        IniField.LOGGER.info("beginSend");
        if (!entity.isAlive() || entity.level.isClientSide) {
            return;
        }
        IniField.LOGGER.info("return1");

        if (entity.isPassenger() || !entity.canChangeDimensions()) {
            return;
        }
        IniField.LOGGER.info("return2");

        if (!forcedEntry || entity.isOnPortalCooldown()) {
            return;
        }
        IniField.LOGGER.info("return3");

        // set a cooldown before this can run again
        entity.setPortalCooldown();

        RegistryKey<World> destination = getDestination(entity);
        ServerWorld aether = Objects.requireNonNull(entity.getServer()).getLevel(destination);

        //MinecraftServer minecraftServer = entity.getServer(); // the server itself
        //ServerWorld aether = minecraftServer.getLevel(DimensionLoader.AETHER);

        if(aether == null){
            IniField.LOGGER.info("Bumblezone: Please restart the server. The Bumblezone dimension hasn't been made yet due to this bug: https://bugs.mojang.com/browse/MC-195468. A restart will fix this.");
            return;
        }


        entity.changeDimension(aether, new AetherTeleporter());

        //if (destination ==  RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Reference.MOD_ID)) && entity instanceof ServerPlayerEntity && forcedEntry) {
            //ServerPlayerEntity playerMP = (ServerPlayerEntity) entity;
            // set respawn point for TF dimension to near the arrival portal, only if we spawn here on world creation
            //playerMP.setRespawnPosition(destination, playerMP.getPosition(), playerMP.getRespawnAngle(), true, false);
        //}
        IniField.LOGGER.info("endSend");

    }
}
