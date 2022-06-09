package com.octenexin.inifield;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.client.ClientEvent;
import com.octenexin.inifield.init.*;
import com.octenexin.inifield.utils.Reference;

import com.octenexin.inifield.world.dimension.BzDimension;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inifield")
public class IniField
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static ResourceLocation locate(String name)
    {
        return new ResourceLocation(Reference.MOD_ID, name);
    }



    /**
     * TODO
     * Inifield will work on these part:
     * Entity:
     *      root
     *          Nc
     *          Hb
     *          Jeb
     *      branch
     *          Ender
     *          Ed
     *          Wither
     *      leaves
     *          Zombie
     *          Skeleton
     *          Creeper
     *          Spider
     *          ElderGuardian
     *          Guardian
     *          ZombiePig
     *          Piglin
     *          Ghast
     *          Blaze
     *          WitherSkeleton
     *          Shulker
     *          Endermite
     *     all need at least 64*64 model
     *     basic animation
     *     2D live
     *     for those who have fighting anim, we try to make it
     *     all those entity appears in strange point, don't have normal ai
     *          for example:
     *              wsSkeleton only appears in "mod pub" and NetherCastle, don't walk randomly, he only finishes things I write
     *              Nc will throw his cbCube on ground when he explain how world works
     *              Jeb will mainly keep sitting in front of his PC
     *     Have strange drops, so I have to add more items
     *
     * items:
     *      weapons
     *          cbCube: can use on hand, press shift+right click will change it into cbSword
     *          EnderPickaxe: don't appear in item bar, when press hot key, it appears with anim
     *          EnderSword: press hot key generate laser light that can reflect on most of blocks
     *          PointerBow: can shoot 10 arrows at the same time and 1 can trace enemy, with particle trace
     *          SynchronizedBlade: freeze you and make everything synchronized(sleep)
     *          SpiderWebGenerator: generate something like hook to help climbing
     *          WitherAxe: looks large, only hold it will scare of most of vallnia no wither mobs, press hot key will add effect
     *          DragonPole: looks large, press hot key will emit dragonFireBall or EnderCrystal
     *          ThrowableTNT: can throw TNT without limit
     *      block
     *          4 cloud
     *          some plants
     *              aether plant
     *                  fern
     *                  snow drop flower
     *              overworld plant
     *                  farm plant?
     *      misc
     *          ???
     *
     *
     * Structure:
     *      Overworld
     *          City Biome
     *              skyScrapers
     *              Mojang
     *              living building
     *              commercial building
     *          Countryside Biome
     *              small house
     *              wide farmland
     *          Nc's tree house
     *          Lake boat house
     *      Nether
     *          Hb's castle
     *      The End
     *          Floating workspace
     *      Aether
     *          World Tree
     *          Database
     *          Small features
     *          Nc's house
     *      Frontier
     *          fake final cube
     *      Extrem
     *          Hb's final castle
     *          Aether like features
     *          metasequoia
     *      Domain
     *          screen
     *          JVM like structure
     *
     * Dialog System
     *      Load dialog: from dialog file into a map
     *      Show dialog: using dialog screen helper
     *          may add options
     *      Dialog-based story event
     *      Built-in coding window
     *
     * Global Modify
     *      Lv: additional bar, calc amount of kills
     *      Growing ability
     *      A whole story mechanic
     *
     *
     *
     * */
    public IniField() {
        // Register the setup method for modloading
        IEventBus bus=FMLJavaModLoadingContext.get().getModEventBus();


        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ZeldaConfig.COMMON_CONFIG);


        //TileEntityLoader.TILE_ENTITIES.register(bus);



//        BzBlockTags.tagInit(); // Done extra early as some features needs the tag wrapper.
//        BzItemTags.tagInit();
//        BzEntityTags.tagInit();
//        BzFluidTags.tagInit();

        //Events
//        forgeBus.addListener(BeeAggression::pickupItemAnger);
//        forgeBus.addListener(EventPriority.LOWEST, BeeAggression::minedBlockAnger); // We want to make sure the block will be broken for angering bees
//        forgeBus.addListener(WanderingTrades::addWanderingTrades);
//        forgeBus.addListener(ModdedBeesBeesSpawning::MobSpawnEvent);
//        forgeBus.addListener(HoneycombBroodEvents::reviveByPotionOfBees);
//        forgeBus.addListener(CombCutterEnchantment::attemptFasterMining);
//        forgeBus.addListener(EventPriority.HIGH, EnderpearlImpact::onPearlHit); // High because we want to cancel other mod's impact checks and stuff if it hits a hive.
//        forgeBus.addGenericListener(Block.class, Bumblezone::missingMappingDimension);
//        forgeBus.addListener(PotionOfBeesBeeSplashPotionProjectile::ProjectileImpactEvent);
//        forgeBus.addGenericListener(Entity.class, CapabilityEntityPosAndDim::onAttachCapabilitiesToEntities);
//        forgeBus.addGenericListener(Entity.class, CapabilityFlyingSpeed::onAttachCapabilitiesToEntities);
//        forgeBus.addListener(EntityTeleportationHookup::entityTick);
//        forgeBus.addListener(BeeAggression::playerTick);
        //forgeBus.addListener(BzWorldSavedData::worldTick);

        //Registration
        //bus.addListener(DataGenerators::gatherData);

        ModStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
        bus.addListener(this::setup);
        //bus.addListener(EventPriority.LOWEST, this::modCompatSetup); //run after all mods
        //bus.addListener(EventPriority.NORMAL, BzEntities::registerEntityAttributes);

        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        //BzFluids.FLUIDS.register(modEventBus);
        ModBiomes.BIOMES.register(bus);

        //BzPOI.POI_TYPES.register(modEventBus);
        //BzItems.RECIPES.register(modEventBus);
        //BzEffects.EFFECTS.register(modEventBus);
        ModFeatures.FEATURES.register(bus);
        ModEntities.ENTITY_TYPES.register(bus);
        //BzSounds.SOUND_EVENTS.register(modEventBus);


        ModParticles.PARTICLE_TYPES.register(bus);
        //BzEnchantments.ENCHANTMENTS.register(modEventBus);
        ModSurfaceBuilders.SURFACE_BUILDERS.register(bus);

        /*I certainly know it's ugly.
          Reflection...
          I don't know what I am doing, 'cause the code is copied from WDA.
          Hope Forge will patch it soon.
        */



        /**
         * I know it's ugly, but forge don't provide trunkPlaceType register
         * so we use 1.12's way to register trunkPlacer.
         * foliagePlacer/treeDecorator have API, so events works on them
         * */
        new ModTreeFeatures();


        // Register the enqueueIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        //MinecraftForge.EVENT_BUS.register(this);
    }


    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            ModProcessors.registerProcessors();
            BzDimension.setupDimension();
            //ModTemplatePools.bootstrap();
            ModConfiguredFeatures.registerConfiguredFeatures();
            ModStructures.setupStructures();
            ModConfiguredStructures.registerConfiguredStructures();
            //at common setup event

        });

//        CapabilityEntityPosAndDim.register();
//        CapabilityFlyingSpeed.register();
    }






}
