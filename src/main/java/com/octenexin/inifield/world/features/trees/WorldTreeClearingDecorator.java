package com.octenexin.inifield.world.features.trees;

import com.mojang.serialization.Codec;
import com.octenexin.inifield.init.ModTreeFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class WorldTreeClearingDecorator extends TreeDecorator {

    public static final Codec<WorldTreeClearingDecorator> CODEC;
    public static final WorldTreeClearingDecorator INSTANCE = new WorldTreeClearingDecorator();

    protected TreeDecoratorType<?> type() {
        return ModTreeFeatures.WORLD_TREE_CLEARING;
    }

    public void place(ISeedReader p_225576_1_, Random p_225576_2_, List<BlockPos> trunkList, List<BlockPos> leavesList, Set<BlockPos> p_225576_5_, MutableBoundingBox boundingBox) {
        int centerX=boundingBox.getCenter().getX();
        int centerZ=boundingBox.getCenter().getZ();
        leavesList.forEach((p_242866_5_) -> {

            if(p_225576_2_.nextInt(4)==0&&p_242866_5_.getX()>centerX-8&&p_242866_5_.getX()<centerX+8&&p_242866_5_.getZ()>centerZ-8&&p_242866_5_.getZ()<centerZ+8){


                if(checkGround(p_225576_1_,p_242866_5_)){
                    for (int i=0;i<p_242866_5_.getY()-1;i++){
                        this.setBlock(p_225576_1_,p_242866_5_.below(i),Blocks.AIR.defaultBlockState(),p_225576_5_,boundingBox);
                    }
                }
            }


        });
    }

    private boolean checkGround(ISeedReader p_225576_1_,BlockPos pos){
        Block curBlock;
        BlockPos tempPos=new BlockPos(pos.getX(),255,pos.getZ());

        for (int i=0;i<254;i++){
            curBlock=p_225576_1_.getBlockState(tempPos.below(i)).getBlock();

            if(curBlock==Blocks.OAK_LOG){
                return false;
            }
            if(curBlock!=Blocks.OAK_LEAVES||curBlock!=Blocks.AIR||curBlock!=Blocks.VINE)break;
        }

        return true;
    }

    static {
        CODEC = Codec.unit(() -> {
            return INSTANCE;
        });
    }

}
