package com.octenexin.inifield.world.features;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.init.ModBlocks;
import com.octenexin.inifield.utils.ModGeneralUtils;
import com.octenexin.inifield.world.features.configs.NbtFeatureConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class FeatureNotchSculpture extends NbtFeature{

    public FeatureNotchSculpture(Codec<NbtFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NbtFeatureConfig config) {

        if(world.getBlockState(position.below()).is(ModBlocks.LIGHT_CLOUD.get())&&random.nextInt(10)==0){
            ResourceLocation nbtRL = ModGeneralUtils.getRandomEntry(config.nbtResourcelocationsAndWeights, random);

            TemplateManager structureManager = world.getLevel().getStructureManager();
            Template template = structureManager.get(nbtRL);
            if(template == null){
                IniField.LOGGER.error("Identifier to the specified nbt file was not found! : {}", nbtRL);
                return false;
            }
            Rotation rotation = Rotation.NONE;

            // For proper offsetting the feature so it rotate properly around position parameter.
            BlockPos halfLengths = new BlockPos(
                    template.getSize().getX() / 2,
                    template.getSize().getY() / 2,
                    template.getSize().getZ() / 2);

            BlockPos.Mutable mutable = new BlockPos.Mutable().set(position);

            // offset the feature's position
            position = position.above(config.structureYOffset);

            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setRotationPivot(halfLengths).setIgnoreEntities(false);
           template.placeInWorld(world, mutable.set(position).move(-halfLengths.getX(), 0, -halfLengths.getZ()), placementsettings, random);


            return true;
        }else {
            return false;
        }


    }
}
