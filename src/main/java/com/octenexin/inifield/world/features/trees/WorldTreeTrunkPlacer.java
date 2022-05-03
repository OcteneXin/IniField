package com.octenexin.inifield.world.features.trees;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.octenexin.inifield.init.ModTreeFeatures;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import org.lwjgl.system.CallbackI;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class WorldTreeTrunkPlacer extends GiantTrunkPlacer {
    public static final Codec<WorldTreeTrunkPlacer> CODEC = RecordCodecBuilder.create((p_236902_0_) -> {
        return trunkPlacerParts(p_236902_0_).apply(p_236902_0_, WorldTreeTrunkPlacer::new);
    });

    public WorldTreeTrunkPlacer(int p_i232058_1_, int p_i232058_2_, int p_i232058_3_) {
        super(p_i232058_1_, p_i232058_2_, p_i232058_3_);
    }

    protected TrunkPlacerType<?> type() {
        return ModTreeFeatures.WORLD_TREE_TRUNK_PLACER;
    }

    private static void placeLogIfFreeWithOffset(IWorldGenerationReader p_236899_0_, Random p_236899_1_, BlockPos.Mutable p_236899_2_, Set<BlockPos> p_236899_3_, MutableBoundingBox p_236899_4_, BaseTreeFeatureConfig p_236899_5_, BlockPos p_236899_6_, int p_236899_7_, int p_236899_8_, int p_236899_9_) {
        p_236899_2_.setWithOffset(p_236899_6_, p_236899_7_, p_236899_8_, p_236899_9_);
        placeLogIfFree(p_236899_0_, p_236899_1_, p_236899_2_, p_236899_3_, p_236899_4_, p_236899_5_);
    }

    private void genColumnLogs(IWorldGenerationReader world, Random rand, int maxHeight, BlockPos pos, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_,int height){
        for(int i=height;i>-3;i--){
            placeLog(world, rand, pos.above(i), p_230382_5_, p_230382_6_, p_230382_7_);
        }
    }

    private void placeHollowTrunk(IWorldGenerationReader world, Random rand,BlockPos pos,int outerRadius,int innerRadius,Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_){
        for (int i=-outerRadius;i<outerRadius;i++){
            for (int j=-outerRadius;j<outerRadius;j++){
                if((i*i+j*j<=outerRadius*outerRadius)&&(i*i+j*j>=innerRadius*innerRadius)){
                    placeLog(world,rand,pos.offset(i,0,j),p_230382_5_,p_230382_6_,p_230382_7_);
                }
            }
        }
    }

    private void genBranches(IWorldGenerationReader world, Random rand, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_, int l, int maxP, float f, int expectAngle, boolean isDense, BlockPos newBlockpos, List<FoliagePlacer.Foliage> list){
        int denseFact=isDense?4:5;

        if(l%denseFact==3&&l<maxP-3){

            if(expectAngle==90){

                BlockPos temp=new BlockPos(newBlockpos);
                BlockPos tempTemp=new BlockPos(temp);
                for(int t=0;t<(int)((maxP-l)/1.5);++t){


                    tempTemp=temp.offset(0,t,0);
                    placeLog(world, rand, tempTemp, p_230382_5_, p_230382_6_, p_230382_7_);

                }
                list.add(new FoliagePlacer.Foliage(tempTemp,0,false));
            }
            else {
                float newF;
                if(l%10==3){
                    newF=f+rand.nextFloat()*0.3F+0.7F;
                }else {
                    newF=f-rand.nextFloat()*0.3F-0.7F;
                }

                int newJ=0;
                int newK=0;

                BlockPos temp=new BlockPos(newBlockpos);
                BlockPos tempTemp=new BlockPos(temp);

                if(expectAngle==30){
                    for(int t=0;t<(int)((maxP-l)/1.15);++t){

                        newJ = (int)(1.5F + MathHelper.cos(newF) * (float)t);
                        newK = (int)(1.5F + MathHelper.sin(newF) * (float)t);

                        tempTemp=temp.offset(newJ,(t/2),newK);
                        placeLog(world, rand, tempTemp, p_230382_5_, p_230382_6_, p_230382_7_);

                    }
                }else if(expectAngle==45){
                    for(int t=0;t<(int)((maxP-l)/1.414)/0.8;++t){

                        newJ = (int)(1.5F + MathHelper.cos(newF) * (float)t);
                        newK = (int)(1.5F + MathHelper.sin(newF) * (float)t);

                        tempTemp=temp.offset(newJ,t,newK);
                        placeLog(world, rand, tempTemp, p_230382_5_, p_230382_6_, p_230382_7_);

                    }
                }else if(expectAngle==60){
                    for(int t=0;t<(int)((maxP-l)/2.0);++t){

                        newJ = (int)(1.5F + MathHelper.cos(newF) * (float)t);
                        newK = (int)(1.5F + MathHelper.sin(newF) * (float)t);

                        tempTemp=temp.offset(newJ,t*2+1,newK);
                        placeLog(world, rand, tempTemp, p_230382_5_, p_230382_6_, p_230382_7_);
                        placeLog(world, rand, tempTemp.below(), p_230382_5_, p_230382_6_, p_230382_7_);

                    }
                }else if(expectAngle==-30){//-30


                    for(int t=0;t<8;++t){

                        newJ = (int)(1.5F + MathHelper.cos(newF) * (float)t);
                        newK = (int)(1.5F + MathHelper.sin(newF) * (float)t);

                        tempTemp=temp.offset(newJ,-(t/2),newK);
                        placeLog(world, rand, tempTemp, p_230382_5_, p_230382_6_, p_230382_7_);

                    }
                }else {//0
                    for(int t=0;t<(int)((maxP-l)/1.5);++t){

                        newJ = (int)(1.5F + MathHelper.cos(newF) * (float)t);
                        newK = (int)(1.5F + MathHelper.sin(newF) * (float)t);

                        tempTemp=temp.offset(newJ,0,newK);
                        placeLog(world, rand, tempTemp, p_230382_5_, p_230382_6_, p_230382_7_);

                    }
                }

                list.add(new FoliagePlacer.Foliage(tempTemp,0,false));
            }


        }
    }

    //pos is ground west north log
    public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader world, Random rand, int maxHeight, BlockPos pos, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_) {
        List<FoliagePlacer.Foliage> list = Lists.newArrayList();

        /*
        *  ##
        * #!!#
        * #!!#
        *  ##
        * */
        //generate root
//        genColumnLogs(world,rand,maxHeight,pos.north(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);
//        genColumnLogs(world,rand,maxHeight,pos.north().east(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);
//        genColumnLogs(world,rand,maxHeight,pos.east().east(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);
//        genColumnLogs(world,rand,maxHeight,pos.east().east().south(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);
//        genColumnLogs(world,rand,maxHeight,pos.east().south().south(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);
//        genColumnLogs(world,rand,maxHeight,pos.south().south(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);
//        genColumnLogs(world,rand,maxHeight,pos.south().west(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);
//        genColumnLogs(world,rand,maxHeight,pos.west(),p_230382_5_,p_230382_6_,p_230382_7_,rand.nextInt(5)+2);

        //generate main trunk
        /*
        * %%%
        * %#%
        * %%%
        * */

        int rootRadius=3;
        for (int i=-rootRadius;i<rootRadius;i++){
            for (int j=-rootRadius;j<rootRadius;j++){
                if(i*i+j*j<=rootRadius*rootRadius){
                    setDirtAt(world,pos.offset(i,-1,j));
                }
            }
        }


        for(int i = 0; i < maxHeight; ++i) {
            if(i<maxHeight/4){
                placeHollowTrunk(world,rand,pos.above(i),3,2,p_230382_5_,p_230382_6_,p_230382_7_);
            }else if(i<maxHeight/2){
                placeHollowTrunk(world,rand,pos.above(i),2,1,p_230382_5_,p_230382_6_,p_230382_7_);
            }else {
                placeHollowTrunk(world,rand,pos.above(i),1,0,p_230382_5_,p_230382_6_,p_230382_7_);
            }
        }



        /**
         * generate branches
         * node4: 1->90 4->60
         * node3: 4->60
         * node2: 2->60 2->45
         * node1: 4->45 4->0 2->-10
         * */

        for(int s=0;s<4;s++){
            int curHeight=(int)(maxHeight*(1-s/5.0));

            switch (s){
                case 0:{
                    //gen 4->60
                    for(int p=0;p<4;p++){

                        float f = ((float)Math.PI*(p/2.0F));
                        int j = 0;
                        int k = 0;

                        BlockPos newBlockpos=pos.offset(j,curHeight,k);
                        for(int l = 0; l < (int)((maxHeight*0.2)/2-1); ++l) {
                            j = (int)(1.5F + MathHelper.cos(f) * (float)l);
                            k = (int)(1.5F + MathHelper.sin(f) * (float)l);
                            placeLog(world, rand, pos.offset(j, curHeight + 2*l, k), p_230382_5_, p_230382_6_, p_230382_7_);
                            placeLog(world, rand, pos.offset(j, curHeight + 2*l-1, k), p_230382_5_, p_230382_6_, p_230382_7_);

                            newBlockpos=pos.offset(j,curHeight+2*l,k);
                        }

                        list.add(new FoliagePlacer.Foliage(newBlockpos, 0, false));
                    }
                }break;
                case 1:{
                    //gen 6->60
                    for(int p=0;p<4;p++){

                        float f = ((float)Math.PI*(p/3.0F))+((float)Math.PI/6);
                        int j = 0;
                        int k = 0;

                        BlockPos newBlockpos=pos.offset(j,curHeight,k);
                        for(int l = 0; l < (int)((maxHeight*0.4)/1.8-2); ++l) {
                            j = (int)(1.5F + MathHelper.cos(f) * (float)l);
                            k = (int)(1.5F + MathHelper.sin(f) * (float)l);
                            placeLog(world, rand, pos.offset(j, curHeight + 2*l, k), p_230382_5_, p_230382_6_, p_230382_7_);
                            placeLog(world, rand, pos.offset(j, curHeight + 2*l-1, k), p_230382_5_, p_230382_6_, p_230382_7_);

                            newBlockpos=pos.offset(j,curHeight+2*l,k);
                            int maxP=(int)((maxHeight*0.4)/1.8-1);
                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,0,false,newBlockpos,list);



                            list.add(new FoliagePlacer.Foliage(newBlockpos, 0, false));
                        }
                    }
                }break;
                case 2:{
                    //4->60
                    for(int p=0;p<4;p++){

                        float f = ((float)Math.PI*(p/2.0F));
                        int j = 0;
                        int k = 0;

                        BlockPos newBlockpos=pos.offset(j,curHeight,k);
                        for(int l = 0; l < (int)((maxHeight*0.6)/1.8*0.8); ++l) {
                            j = (int)(1.5F + MathHelper.cos(f) * (float)l);
                            k = (int)(1.5F + MathHelper.sin(f) * (float)l);
                            placeLog(world, rand, pos.offset(j, curHeight + 2*l, k), p_230382_5_, p_230382_6_, p_230382_7_);
                            placeLog(world, rand, pos.offset(j, curHeight + 2*l-1, k), p_230382_5_, p_230382_6_, p_230382_7_);

                            newBlockpos=pos.offset(j,curHeight+2*l,k);

                            int maxP=(int)((maxHeight*0.6)/1.8*0.8);
                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,30,false,newBlockpos,list);



                        }

                        list.add(new FoliagePlacer.Foliage(newBlockpos, 0, false));
                    }
                    //6->45
                    for(int p=0;p<6;p++){

                        float f = ((float)Math.PI*(p/3.0F))+((float)Math.PI/6);
                        int j = 0;
                        int k = 0;

                        BlockPos newBlockpos=pos.offset(j,curHeight,k);
                        for(int l = 0; l < (int)((maxHeight*0.6)/1.414); ++l) {
                            j = (int)(1.5F + MathHelper.cos(f) * (float)l);
                            k = (int)(1.5F + MathHelper.sin(f) * (float)l);
                            placeLog(world, rand, pos.offset(j, curHeight + l, k), p_230382_5_, p_230382_6_, p_230382_7_);

                            newBlockpos=pos.offset(j,curHeight+l,k);
                            int maxP=(int)((maxHeight*0.6)/1.414);

                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,90,false,newBlockpos,list);
                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,-30,true,newBlockpos,list);



                        }

                        list.add(new FoliagePlacer.Foliage(newBlockpos, 0, false));
                    }

                }break;
                case 3:{
                    //6->45
                    for(int p=0;p<6;p++){

                        float f = ((float)Math.PI*(p/3.0F));
                        int j = 0;
                        int k = 0;

                        BlockPos newBlockpos=pos.offset(j,curHeight,k);
                        int maxP=(int)(((maxHeight)/0.8)/1.414*0.75-3);
                        for(int l = 0; l < maxP; ++l) {
                            j = (int)(1.5F + MathHelper.cos(f) * (float)l);
                            k = (int)(1.5F + MathHelper.sin(f) * (float)l);
                            placeLog(world, rand, pos.offset(j, curHeight + l, k), p_230382_5_, p_230382_6_, p_230382_7_);
                            newBlockpos=pos.offset(j,curHeight+l,k);


                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,30,true,newBlockpos,list);
                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,90,false,newBlockpos,list);




                        }

                        list.add(new FoliagePlacer.Foliage(newBlockpos, 0, false));
                    }
                    //6->30

                    //8->0
                    for(int p=0;p<8;p++){

                        float f = ((float)Math.PI*(p/4.0F));
                        int j = 0;
                        int k = 0;
                        int tempY=curHeight;

                        BlockPos newBlockpos=pos.offset(j,curHeight,k);
                        int maxP=(int)(((maxHeight)*0.8));
                        for(int l = 0; l < maxP; ++l) {
                            j = (int)(1.5F + MathHelper.cos(f) * (float)l);
                            k = (int)(1.5F + MathHelper.sin(f) * (float)l);
                            if(l%3==0)tempY++;
                            placeLog(world, rand, pos.offset(j, tempY, k), p_230382_5_, p_230382_6_, p_230382_7_);
                            newBlockpos=pos.offset(j,tempY,k);

                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,30,false,newBlockpos,list);
                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,60,true,newBlockpos,list);
                            genBranches(world,rand,p_230382_5_,p_230382_6_,p_230382_7_,l,maxP,f,-30,true,newBlockpos,list);




                        }

                        list.add(new FoliagePlacer.Foliage(newBlockpos, 0, false));
                    }
                }

            }


        }

        return list;
    }
}
