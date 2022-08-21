package com.octenexin.inifield.init;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.block.WeedGrass;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.features.ScatteredWeedGrassFeature;
import com.octenexin.inifield.world.features.trees.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * write ahead:
 * this register only works for:
 *  Features themselves
 *  trunk placers
 *  foliage placers
 *  decorators
 *
 * */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTreeFeatures {

    //public static final RuleTest HONEYCOMB_BUMBLEZONE = new TagMatchRuleTest(BzBlockTags.HONEYCOMBS_THAT_FEATURES_CAN_CARVE);
      private static final List<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = new ArrayList<>();
      private static final List<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = new ArrayList<>();
//
      public static final TrunkPlacerType<WorldTreeTrunkPlacer> WORLD_TREE_TRUNK_PLACER=registerTrunk(IniField.locate("world_tree_trunk_placer"),WorldTreeTrunkPlacer.CODEC);
//    public static final TrunkPlacerType<TrunkRiser> TRUNK_RISER = registerTrunk(TwilightForestMod.prefix("trunk_mover_upper"), TrunkRiser.CODEC);
//    public static final TrunkPlacerType<HollowTrunkPlacer> HOLLOW_TRUNK = registerTrunk(TwilightForestMod.prefix("hollow_trunk_placer"), HollowTrunkPlacer.CODEC);

      public static final FoliagePlacerType<WorldTreeFoliagePlacer> WORLD_TREE_FOLIAGE_PLACER = registerFoliage(IniField.locate("world_tree_foliage_placer"), WorldTreeFoliagePlacer.CODEC);

      public static final TreeDecoratorType<WorldTreeVineDecorator> WORLD_TREE_VINE = registerTreeFeature(IniField.locate("world_tree_vine_decorator"),WorldTreeVineDecorator.CODEC);
      public static final TreeDecoratorType<WorldTreeClearingDecorator> WORLD_TREE_CLEARING = registerTreeFeature(IniField.locate("world_tree_clearing_decorator"),WorldTreeClearingDecorator.CODEC);
//
//    public static final TreeDecoratorType<TrunkSideDecorator> TRUNKSIDE_DECORATOR = registerTreeFeature(TwilightForestMod.prefix("trunkside_decorator"), TrunkSideDecorator.CODEC);
//    public static final TreeDecoratorType<TreeRootsDecorator> TREE_ROOTS = registerTreeFeature(TwilightForestMod.prefix("tree_roots"), TreeRootsDecorator.CODEC);
//    public static final TreeDecoratorType<DangleFromTreeDecorator> DANGLING_DECORATOR = registerTreeFeature(TwilightForestMod.prefix("dangle_from_tree_decorator"), DangleFromTreeDecorator.CODEC);

      public static final Placement<ChanceConfig> WORLD_TREE_PLACEMENT = new WorldTreePlacement(ChanceConfig.CODEC);


      public static final ConfiguredPlacement<?> CONFIGURED_WORLD_TREE_PLACEMENT = WORLD_TREE_PLACEMENT.configured(new ChanceConfig(50));
//    public static final ConfiguredPlacement<?> CONFIGURED_PLACEMENT_NOTFSTRUCTURE_WITHHEIGHTMAP = CONFIGURED_PLACEMENT_NOTFSTRUCTURE.withPlacement(Placement.HEIGHTMAP.configure(NoPlacementConfig.INSTANCE));



    private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(ResourceLocation name, Codec<P> codec) {
        FoliagePlacerType<P> type = new FoliagePlacerType<>(codec);
        type.setRegistryName(name);
        FOLIAGE_PLACER_TYPES.add(type);
        return type;
    }

    private static <P extends TreeDecorator> TreeDecoratorType<P> registerTreeFeature(ResourceLocation name, Codec<P> codec) {
        TreeDecoratorType<P> type = new TreeDecoratorType<P>(codec);
        type.setRegistryName(name);
        TREE_DECORATOR_TYPES.add(type);
        return type;
    }

    private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> registerTrunk(ResourceLocation name, Codec<P> codec) {
        return Registry.register(Registry.TRUNK_PLACER_TYPES, name, new TrunkPlacerType<>(codec));
    }

//
//    protected static <FC extends IFeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> registerWorldFeature(ResourceLocation rl, ConfiguredFeature<FC, F> feature) {
//        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, rl, feature);
//    }
//
    @SubscribeEvent
    public static void registerFoliagePlacers(RegistryEvent.Register<FoliagePlacerType<?>> evt) {
        evt.getRegistry().registerAll(FOLIAGE_PLACER_TYPES.toArray(new FoliagePlacerType<?>[0]));
    }

    @SubscribeEvent
    public static void registerTreeDecorators(RegistryEvent.Register<TreeDecoratorType<?>> evt) {
        evt.getRegistry().registerAll(TREE_DECORATOR_TYPES.toArray(new TreeDecoratorType<?>[0]));
    }

    @SubscribeEvent
    public static void registerPlacementConfigs(RegistryEvent.Register<Placement<?>> evt) {
        evt.getRegistry().register(WORLD_TREE_PLACEMENT.setRegistryName(IniField.locate("world_tree_placement")));
    }
}
