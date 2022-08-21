package com.octenexin.inifield.event;


import com.mojang.serialization.Codec;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.client.gui.SimpleDialog;
import com.octenexin.inifield.client.particles.WeedFurryParticleData;
import com.octenexin.inifield.init.ModConfiguredStructures;
import com.octenexin.inifield.init.ModItems;
import com.octenexin.inifield.init.ModStructures;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.dimension.AetherTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EditBookScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
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
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
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
    public static void onEnderPickaxeHit(LivingHurtEvent event){
        if(event.getSource().getDirectEntity() instanceof PlayerEntity){

            PlayerEntity playerEntity= (PlayerEntity) event.getSource().getDirectEntity();
            if(playerEntity.getMainHandItem().getItem()!= ModItems.ENDER_PICKAXE.get()){
                return;
            }

            LivingEntity consumer=event.getEntityLiving();

            //event.setAmount((float) consumer.getAttributes().getValue(Attributes.MAX_HEALTH));
            consumer.kill();
        }
    }

//    @SubscribeEvent
//    public static void onEnderPickaxeMine(PlayerInteractEvent.RightClickBlock event){
//
//
//
//
//        PlayerEntity player=event.getPlayer();
//        if(player.getMainHandItem().getItem()!= ModItems.ENDER_PICKAXE.get()){
//            return;
//        }
//
//        World world=player.level;
//        BlockPos pos=event.getPos();
//        Block block=world.getBlockState(pos).getBlock();
//        world.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
//        player.spawnAtLocation(new ItemStack(block.asItem()));
//
//    }

    @SubscribeEvent
    public static void onEnderPickaxeFinish(LivingEntityUseItemEvent.Finish event){
        LivingEntity entity=event.getEntityLiving();

        if(entity instanceof PlayerEntity){

            if(entity.getMainHandItem().getItem()!= ModItems.ENDER_PICKAXE.get()){
                return;
            }


            World world=entity.level;
            BlockPos pos=entity.blockPosition();

            double d0 = 16;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(entity.position()).inflate(d0, 10.0D, d0);

            world.getLoadedEntitiesOfClass(LivingEntity.class, axisalignedbb).stream()
                    .filter((p_241408_1_) -> p_241408_1_ != entity)
                    .forEach(LivingEntity::kill);


            world.addParticle(ParticleTypes.CRIT,pos.getX(),pos.getY()+2.0D,pos.getZ(),0.0D,0.0D,0.0D);

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



}
