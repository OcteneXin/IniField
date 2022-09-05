package com.octenexin.inifield.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.octenexin.inifield.utils.Reference;
import com.octenexin.inifield.world.dimension.layer.BzBiomeLayer;
import com.octenexin.inifield.world.dimension.layer.BzBiomeMergeLayer;
import com.octenexin.inifield.world.dimension.layer.BzBiomeNonstandardLayer;
import com.octenexin.inifield.world.dimension.layer.BzBiomeScaleLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

public class AetherBiomeProvider extends BiomeProvider {
    public static void registerBiomeProvider() {
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(Reference.MOD_ID, "biome_source"), AetherBiomeProvider.CODEC);
    }

    public static final Codec<AetherBiomeProvider> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                    RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((vanillaLayeredBiomeSource) -> vanillaLayeredBiomeSource.BIOME_REGISTRY))
            .apply(instance, instance.stable(AetherBiomeProvider::new)));




    public static ResourceLocation AETHER_PLAIN = new ResourceLocation(Reference.MOD_ID, "aether_plain");
    public static ResourceLocation FURRY_CLOUD_PLAIN = new ResourceLocation(Reference.MOD_ID, "furry_cloud_plain");
    public static ResourceLocation METASEQUOIA_PATCH = new ResourceLocation(Reference.MOD_ID, "metasequoia_patch");




    private final Layer BIOME_SAMPLER;
    private final Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> LAYERS_BIOME_REGISTRY;
    public static List<Biome> NONSTANDARD_BIOME = new ArrayList<>();

    public AetherBiomeProvider(Registry<Biome> biomeRegistry) {
        this(0, biomeRegistry);
    }
    public AetherBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
        super(biomeRegistry.entrySet().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(Reference.MOD_ID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));

        NONSTANDARD_BIOME = this.possibleBiomes.stream()
                .filter(biome ->  {
                    ResourceLocation rlKey = biomeRegistry.getKey(biome);
                    return rlKey.equals(METASEQUOIA_PATCH);
                })
                .collect(Collectors.toList());

        BzBiomeLayer.setSeed(seed);
        this.BIOME_REGISTRY = biomeRegistry;
        AetherBiomeProvider.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.BIOME_SAMPLER = buildWorldProcedure(seed);
    }

    public static Layer buildWorldProcedure(long seed) {
        IAreaFactory<LazyArea> layerFactory = makeLayers((context) -> new LazyAreaLayerContext(25, seed, context));
        return new Layer(layerFactory);
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> makeLayers(LongFunction<C> seed) {
        IAreaFactory<T> biomes = BzBiomeLayer.INSTANCE.run(seed.apply(200L));

        biomes = new BzBiomeScaleLayer(AETHER_PLAIN).run(seed.apply(1055L), biomes);
        biomes = ZoomLayer.NORMAL.run(seed.apply(2427L), biomes);
        biomes = ZoomLayer.NORMAL.run(seed.apply(2426L), biomes);
        biomes = ZoomLayer.NORMAL.run(seed.apply(2425L), biomes);
        biomes = ZoomLayer.FUZZY.run(seed.apply(2423L), biomes);
        biomes = ZoomLayer.FUZZY.run(seed.apply(1001L), biomes);

        IAreaFactory<T> layerOverlay = BzBiomeNonstandardLayer.INSTANCE.run(seed.apply(204L));
        layerOverlay = ZoomLayer.NORMAL.run(seed.apply(2423L), layerOverlay);
        layerOverlay = ZoomLayer.NORMAL.run(seed.apply(2503L), layerOverlay);
        layerOverlay = ZoomLayer.NORMAL.run(seed.apply(2603L), layerOverlay);
        layerOverlay = ZoomLayer.FUZZY.run(seed.apply(2853L), layerOverlay);
        layerOverlay = ZoomLayer.FUZZY.run(seed.apply(3583L), layerOverlay);

        biomes = BzBiomeMergeLayer.INSTANCE.run(seed.apply(5583L), layerOverlay, biomes);

        return biomes;
    }

    public static Layer makeLayers(long seed, Registry<Biome> registry) {
        IAreaFactory<LazyArea> areaFactory = makeLayers((context) -> new LazyAreaLayerContext(25, seed, context));
        // Debug code to render an image of the biome layout within the ide
		/*final Map<Integer, Integer> remapColors = new HashMap<>();
		remapColors.put(getBiomeId(TFBiomes.tfLake, registry), 0x0000FF);
		remapColors.put(getBiomeId(TFBiomes.twilightForest, registry), 0x00FF00);
		remapColors.put(getBiomeId(TFBiomes.denseTwilightForest, registry), 0x00AA00);
		remapColors.put(getBiomeId(TFBiomes.highlands, registry), 0xCC6900);
		remapColors.put(getBiomeId(TFBiomes.mushrooms, registry), 0xcc008b);
		remapColors.put(getBiomeId(TFBiomes.tfSwamp, registry), 0x00ccbb);
		remapColors.put(getBiomeId(TFBiomes.stream, registry), 0x0000FF);
		remapColors.put(getBiomeId(TFBiomes.snowy_forest, registry), 0xFFFFFF);
		remapColors.put(getBiomeId(TFBiomes.glacier, registry), 0x82bff5);
		remapColors.put(getBiomeId(TFBiomes.clearing, registry), 0x84f582);
		remapColors.put(getBiomeId(TFBiomes.oakSavanna, registry), 0xeff582);
		remapColors.put(getBiomeId(TFBiomes.fireflyForest, registry), 0x58fc66);
		remapColors.put(getBiomeId(TFBiomes.deepMushrooms, registry), 0xb830b8);
		remapColors.put(getBiomeId(TFBiomes.darkForest, registry), 0x193d0d);
		remapColors.put(getBiomeId(TFBiomes.enchantedForest, registry), 0x00FFFF);
		remapColors.put(getBiomeId(TFBiomes.fireSwamp, registry), 0xFF0000);
		remapColors.put(getBiomeId(TFBiomes.darkForestCenter, registry), 0xFFFF00);
		remapColors.put(getBiomeId(TFBiomes.finalPlateau, registry), 0x000000);
		remapColors.put(getBiomeId(TFBiomes.thornlands, registry), 0x3d250d);
		remapColors.put(getBiomeId(TFBiomes.spookyForest, registry), 0x7700FF);
		BufferedImage image = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);
		Graphics2D display = image.createGraphics();
		LazyArea area = areaFactory.make();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int z = 0; z < image.getHeight(); z++) {
				int c = area.getValue(x, z);
				display.setColor(new Color(remapColors.getOrDefault(c, c)));
				display.drawRect(x, z, 1, 1);
			}
		}
 		System.out.println("breakpoint");*/
        return new Layer(areaFactory) {
            @Override
            public Biome get(Registry<Biome> p_242936_1_, int p_242936_2_, int p_242936_3_) {
                int i = this.area.get(p_242936_2_, p_242936_3_);
                Biome biome = registry.byId(i);
                if (biome == null)
                    throw new IllegalStateException("Unknown biome id emitted by layers: " + i);
                return biome;
            }
        };
    }


    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.BIOME_SAMPLER.get(BIOME_REGISTRY,x,z);
    }


    @Override
    protected Codec<? extends BiomeProvider> codec() {
        return CODEC;
    }

    @Override
    // CLIENT-SIDED
    public BiomeProvider withSeed(long seed) {
        return new AetherBiomeProvider(seed, this.BIOME_REGISTRY);
    }
}
