package com.octenexin.inifield.event;


import com.mojang.serialization.Codec;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.client.gui.SimpleDialog;
import com.octenexin.inifield.client.particles.WeedFurryParticleData;
import com.octenexin.inifield.init.ModConfiguredStructures;
import com.octenexin.inifield.init.ModStructures;
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.octenexin.inifield.utils.ModGeneralUtils.attemptSendPlayer;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CommonEvent {

    private static Method GETCODEC_METHOD;

    @SubscribeEvent
    public static void biomeModification(BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        if (types.contains(net.minecraftforge.common.BiomeDictionary.Type.OVERWORLD) && (types.contains(net.minecraftforge.common.BiomeDictionary.Type.MOUNTAIN) || types.contains(net.minecraftforge.common.BiomeDictionary.Type.COLD))) {
            event.getGeneration().getStructures().add(() -> {
                return ModConfiguredStructures.CONFIGURED_HIGHWAY;
            });
        }
        IniField.LOGGER.debug("biomeModification!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    }

    @SubscribeEvent
    public static void addDimensionalSpacing(WorldEvent.Load event) {


        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            try {
                if (GETCODEC_METHOD == null) {
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_", new Class[0]);
                }

                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec)GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
                    return;
                }
            } catch (Exception var4) {
                IniField.LOGGER.error("Was unable to check if " + serverWorld.getChunkSource().generator + " is using Terraforged's ChunkGenerator.");
            }

            if (serverWorld.getChunkSource().generator instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap(serverWorld.getChunkSource().generator.getSettings().structureConfig);
            tempMap.putIfAbsent(ModStructures.HIGHWAY.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.HIGHWAY.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;

            IniField.LOGGER.debug("addDimensionalSpacing!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

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

        }
    }


}
