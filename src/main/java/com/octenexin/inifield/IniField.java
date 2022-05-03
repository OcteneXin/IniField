package com.octenexin.inifield;

import com.octenexin.inifield.client.InifieldClient;
import com.octenexin.inifield.init.*;
import com.octenexin.inifield.utils.Reference;

import com.octenexin.inifield.world.dimension.BzDimension;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

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
        bus.addListener(EventPriority.NORMAL, this::setup);
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
        //ModStructures.STRUCTURES.register(bus);

        ModPlacements.DECORATORS.register(bus);
        ModParticles.PARTICLE_TYPES.register(bus);
        //BzEnchantments.ENCHANTMENTS.register(modEventBus);
        ModSurfaceBuilders.SURFACE_BUILDERS.register(bus);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> InifieldClient::subscribeClientEvents);

        /**
         * I know it's ugly, but forge don't provide trunkPlaceType register
         * so we use 1.12's way to register trunkPlacer
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

            //BzCriterias.registerCriteriaTriggers();
            ModProcessors.registerProcessors();
            BzDimension.setupDimension();
            ModConfiguredFeatures.registerConfiguredFeatures();
            //ModConfiguredStructureFeatures.registerConfiguredStructureFeatures();
            //BzEntities.registerAdditionalEntityInformation();
            //ModStructures.setupStructures();

        });

//        CapabilityEntityPosAndDim.register();
//        CapabilityFlyingSpeed.register();
    }


}
