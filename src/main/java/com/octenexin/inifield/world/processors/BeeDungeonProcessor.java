package com.octenexin.inifield.world.processors;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.init.ModProcessors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class BeeDungeonProcessor extends StructureProcessor {

    public static final Codec<BeeDungeonProcessor> CODEC = Codec.unit(BeeDungeonProcessor::new);
    private BeeDungeonProcessor() { }

    @Override
    public Template.BlockInfo processBlock(IWorldReader worldView, BlockPos pos, BlockPos blockPos, Template.BlockInfo structureBlockInfoLocal, Template.BlockInfo structureBlockInfoWorld, PlacementSettings structurePlacementData) {
        BlockState blockState = structureBlockInfoWorld.state;
        BlockPos worldPos = structureBlockInfoWorld.pos;
        Random random = new SharedSeedRandom();
        random.setSeed(worldPos.asLong() * worldPos.getY());



        return new Template.BlockInfo(worldPos, blockState, structureBlockInfoWorld.nbt);
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return ModProcessors.BEE_DUNGEON_PROCESSOR;
    }
}