package com.octenexin.inifield.world.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import org.lwjgl.system.CallbackI;

import java.util.Comparator;
import java.util.Random;


public class CountrysideWheatSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public CountrysideWheatSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    private static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
    private static final BlockState DIRT = Blocks.DIRT.defaultBlockState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.defaultBlockState();
    private static final BlockState FARMLAND=Blocks.FARMLAND.defaultBlockState().setValue(FarmlandBlock.MOISTURE,0);
    private static final BlockState WHEAT=Blocks.WHEAT.defaultBlockState().setValue(CropsBlock.AGE,7);
    private static final BlockState GRASSPATH=Blocks.GRASS_PATH.defaultBlockState();

    private static final SurfaceBuilderConfig CONFIG_GRASS = new SurfaceBuilderConfig(GRASS_BLOCK, DIRT, GRAVEL);
    private static final SurfaceBuilderConfig CONFIG_FARMLAND = new SurfaceBuilderConfig(FARMLAND,DIRT, GRAVEL);
    private static final SurfaceBuilderConfig CONFIG_GRASSPATH = new SurfaceBuilderConfig(GRASSPATH,DIRT, GRAVEL);


    public void apply(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        //creates the default surface normally
        if(noise>1.0D){
            SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CONFIG_GRASS);
        }else if(noise>0.3D){
            SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CONFIG_GRASSPATH);
        }else {
            SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CONFIG_FARMLAND);
        }
    }
}