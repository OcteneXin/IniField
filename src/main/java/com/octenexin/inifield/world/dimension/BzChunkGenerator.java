package com.octenexin.inifield.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.octenexin.inifield.init.ModBlocks;
import com.octenexin.inifield.utils.Reference;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class BzChunkGenerator extends NoiseChunkGenerator {
    public static void registerChunkgenerator() {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Reference.MOD_ID, "chunk_generator"), BzChunkGenerator.CODEC);
    }

    public static final Codec<BzChunkGenerator> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.biomeSource),
                    DimensionStructuresSettings.CODEC.fieldOf("structures").forGetter((ChunkGenerator::getSettings))
            ).apply(instance, instance.stable(BzChunkGenerator::new)));



    public BzChunkGenerator(BiomeProvider biomeSource, DimensionStructuresSettings dimensionStructuresSettings) {
        super(biomeSource, 0,
                ()->{
                    return new DimensionSettings(
                            new DimensionStructuresSettings(true),
                            new NoiseSettings(
                                    128,
                                    new ScalingSettings(
                                            1.0D,
                                            2.0D,
                                            80.0D,
                                            160.0D),
                                    new SlideSettings(
                                            -3000,
                                            64,
                                            -46),
                                    new SlideSettings(
                                            -30,
                                            7,
                                            1),
                                    2,
                                    1,
                                    0.0D,
                                    0.0D,
                                    true,
                                    false,
                                    false,
                                    false),
                            ModBlocks.NORMAL_RAINING_CLOUD.get().defaultBlockState(),
                            Blocks.WATER.defaultBlockState(),
                            -10,
                            -10,
                            0,
                            false);

                }

        );

    }


    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    // CLIENT-SIDED
    public ChunkGenerator withSeed(long seed) {
        return new BzChunkGenerator(this.biomeSource.withSeed(seed), this.getSettings());
    }

    /**
     * For spawning specific mobs in certain places like structures.
     */
    @Override
    public List<MobSpawnInfo.Spawners> getMobsAt(Biome biome, StructureManager accessor, EntityClassification group, BlockPos pos) {
        return super.getMobsAt(biome, accessor, group, pos);
    }

    /**
     * Dedicated to spawning slimes/bees when generating chunks initially.
     * This is so there's lots of bees and the slime can spawn despite the
     * slime having extremely restrictive spawning mechanics.
     * <p>
     * Also spawns bees with chance to bee full of pollen
     * <p>
     * This is mainly vanilla code but with biome$spawnlistentry changed to
     * use bee/slime and the restrictive terrain check called on the entity removed.
     * The height is also restricted so the mob cannot spawn on the ceiling of this
     * dimension as well.
     */
    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
/*        int xChunk = region.getCenterX();
        int zChunk = region.getCenterZ();
        int xCord = xChunk << 4;
        int zCord = zChunk << 4;
        Biome biome = region.getBiome((new ChunkPos(xChunk, zChunk)).getWorldPosition());
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        sharedseedrandom.setDecorationSeed(region.getSeed(), xCord, zCord);
        */
    }



    @Override
    public int getSeaLevel() {
        return -10;
    }
}