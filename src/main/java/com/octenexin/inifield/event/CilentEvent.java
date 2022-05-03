package com.octenexin.inifield.event;


import com.octenexin.inifield.IniField;
import com.octenexin.inifield.client.particles.WeedFurryParticleData;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.dimension.AetherTeleporter;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CilentEvent {

    @SubscribeEvent
    public static void onSetUpEvent(BiomeLoadingEvent event) {

        IniField.LOGGER.debug("onSetup!----------------------------------------");
        //buggy code
//        for (Biome biome : ForgeRegistries.BIOMES) {
//            biome.getGenerationSettings().features.get(6).add(
//                    ()-> Feature.ORE.configured(
//                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
//                                    Blocks.GLOWSTONE.defaultBlockState(),
//                                    8)
//                    ).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(30, 20)))
//                            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(0,0,16)))
//                    .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
//            );
//            biome.getGenerationSettings().features.get(8).add(
//                    ()->ModConfiguredFeatures.WEED_GRASS
//            );
//
//            IniField.LOGGER.debug("inject into {} biome", biome.getRegistryName());
//        }
    }



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


        if(aether == null){
            IniField.LOGGER.info("IniField:Please restart the server. The dimension hasn't been made yet due to this bug: https://bugs.mojang.com/browse/MC-195468. Believe me, a restart will fix this.");
            return;
        }


        entity.changeDimension(aether, new AetherTeleporter(destination));

        //if (destination ==  RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Reference.MOD_ID)) && entity instanceof ServerPlayerEntity && forcedEntry) {
            //ServerPlayerEntity playerMP = (ServerPlayerEntity) entity;
            // set respawn point for TF dimension to near the arrival portal, only if we spawn here on world creation
            //playerMP.setRespawnPosition(destination, playerMP.getPosition(), playerMP.getRespawnAngle(), true, false);
        //}
        IniField.LOGGER.info("endSend");

    }
}
