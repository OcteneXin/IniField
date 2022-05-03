package com.octenexin.inifield.block;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.client.particles.WeedFurryParticle;
import com.octenexin.inifield.client.particles.WeedFurryParticleData;
import com.octenexin.inifield.init.ModParticles;
import net.minecraft.block.*;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.Random;

public class WeedGrass extends BushBlock implements IWaterLoggable, IGrowable {

    //handle block state
    private static final IntegerProperty WEED_TYPE=IntegerProperty.create("weed_type", 0, 3);
    protected static final VoxelShape SHAPE = Block.box(2.5D, 0.0D, 2.5D, 12.5D, 14.0D, 12.5D);
    public static final IntegerProperty AGE;//0:can grow,1:keep height
    public static final IntegerProperty TYPE;//0:root,1:slim,2:leaves,3:furry
    public static final BooleanProperty WATERLOGGED;//0:above water,1:below water

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(new Property[]{AGE, TYPE, WATERLOGGED});
    }

    static {
        AGE = BlockStateProperties.AGE_1;//age
        TYPE= WEED_TYPE;//weed_type
        WATERLOGGED= BlockStateProperties.WATERLOGGED;
    }

    public WeedGrass(AbstractBlock.Properties p_i48780_1_) {
        super(p_i48780_1_);
        this.registerDefaultState(
                (BlockState)((BlockState)((BlockState)this.stateDefinition.any())
                        .setValue(AGE,0)
                        .setValue(TYPE,0)
                        .setValue(WATERLOGGED, false)
        ));

    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return VoxelShapes.empty();
    }

    public FluidState getFluidState(BlockState p_204507_1_) {
        return (Boolean)p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }

    //handle put and die

    //proper block&&water||this below, survive
    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        BlockPos blockpos = p_196260_3_.below();
        BlockState blockstate = p_196260_2_.getBlockState(blockpos);
        if(blockstate.is(this)){
            return true;
        }else {
            return onProperBlock(blockstate.getBlock());
        }

    }

    private BlockPos getTopPos(IBlockReader world,BlockPos randomPosOnWeed){
        int y=randomPosOnWeed.getY();
        BlockPos pos=new BlockPos(randomPosOnWeed);

        while((y<255)&&(world.getBlockState(pos.above()).is(this))){
            pos=pos.above();
            y++;
        }

        return pos;
    }

    private BlockPos getBottomPos(IBlockReader world,BlockPos randomPosOnWeed){
        int y=randomPosOnWeed.getY();
        BlockPos pos=new BlockPos(randomPosOnWeed);

        while((y>1)&&(world.getBlockState(pos.below()).is(this))){
            pos=pos.below();
            y--;
        }

        return pos;
    }

    private int getPlantHeight(IBlockReader world,BlockPos randomPosOnWeed){
        return getTopPos(world,randomPosOnWeed).getY()-getBottomPos(world,randomPosOnWeed).getY()+1;
    }

    //about tick
    public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        if (!p_225534_1_.canSurvive(p_225534_2_, p_225534_3_)) {
            p_225534_2_.destroyBlock(p_225534_3_, true);
        }

    }

    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return (Integer)p_149653_1_.getValue(AGE) == 0;
    }

    public void randomTick(BlockState blockState, ServerWorld world, BlockPos pos, Random random) {
        if (blockState.getValue(AGE) == 0 && (world.isEmptyBlock(pos.above())||world.isWaterAt(pos.above())) && world.getRawBrightness(pos.above(), 0) >= 7) {
            int i= getPlantHeight(world,pos);

            if (i < 8) {
                if(ForgeHooks.onCropsGrowPre(world,pos,blockState,random.nextInt(3) == 0)){
                    this.growWeed(blockState,world,pos);
                    ForgeHooks.onCropsGrowPost(world,pos,blockState);
                }else if(random.nextInt(3)==0) {
                    world.setBlock(pos,blockState.setValue(AGE,1),2);
                }
            }else {
                world.setBlock(pos,blockState.setValue(AGE,1),2);
            }
        }

        if(blockState.getValue(TYPE)==3) spawnWeedFurryParticles(world,pos,blockState);

    }

    /**
     * Starts checking if the block can take the particle and if so and it passes another rng to reduce spawnrate, it then
     * takes the block's dimensions and passes into methods to spawn the actual particle
     */
    private void spawnWeedFurryParticles(World world, BlockPos position, BlockState blockState) {
        if (world.random.nextFloat() < 0.08F)
        {
            this.addWeedFurryParticle(world, position, blockState.getShape(world,position), position.getY()+0.05D);
        }
    }


    /**
     * intermediary method to apply the blockshape and ranges that the particle can spawn in for the next addHoneyParticle
     * method
     */
    private void addWeedFurryParticle(World world, BlockPos blockPos, VoxelShape blockShape, double height) {
        this.addWeedFurryParticle(
                world,
                blockPos.getX() + blockShape.min(Direction.Axis.X),
                blockPos.getX() + blockShape.max(Direction.Axis.X),
                blockPos.getZ() + blockShape.min(Direction.Axis.Z),
                blockPos.getZ() + blockShape.max(Direction.Axis.Z),
                height);
    }


    /**
     * Adds the actual honey particle into the world within the given range
     */
    private void addWeedFurryParticle(World world, double xMin, double xMax, double zMax, double zMin, double yHeight) {
        world.addParticle(
                new WeedFurryParticleData(255,255,255),
                MathHelper.lerp(world.random.nextDouble(), xMin, xMax),
                yHeight,
                MathHelper.lerp(world.random.nextDouble(), zMax, zMin),
                0.0D,
                0.05D,
                0.0D);
    }


    public void growWeed(BlockState blockState, World world, BlockPos pos) {

        IniField.LOGGER.debug("----------------------------------");
        IniField.LOGGER.debug("original y: "+pos.getY());

        //transform to top block
        pos=this.getTopPos(world,pos);
        blockState=world.getBlockState(pos);


        IniField.LOGGER.debug("top y: "+pos.getY());

        //normal logic
        BlockState stateAbove=world.getBlockState(pos.above());
        boolean waterLogged=false;
        int type=0;
        int typeBelow=0;

        if(blockState.is(this)){
            if(blockState.getValue(TYPE)==0){//root
                IniField.LOGGER.debug("down root");
                if(stateAbove.isAir()){
                    waterLogged=false;
                    type=2;
                    IniField.LOGGER.debug("gen leave air");
                }else{
                    waterLogged=true;
                    type=1;
                    IniField.LOGGER.debug("gen slim water");
                }
                typeBelow=0;
            }else if(blockState.getValue(TYPE)==1){//slim
                IniField.LOGGER.debug("down slim");
                if(blockState.getValue(WATERLOGGED)){
                    IniField.LOGGER.debug("down slim water");
                    if(stateAbove.isAir()){
                        waterLogged=false;
                        type=2;
                        IniField.LOGGER.debug("gen leave air");
                    }else{
                        waterLogged=true;
                        type=1;
                        IniField.LOGGER.debug("gen slim water");
                    }
                }else {
                    waterLogged=false;
                    type=3;
                    IniField.LOGGER.debug("gen furry air");
                }
                typeBelow=1;
            }else if(blockState.getValue(TYPE)==2){//leaves
                waterLogged=false;
                type=1;
                typeBelow=2;
                IniField.LOGGER.debug("gen slim air");
            }else {//furry
                waterLogged=false;
                type=3;
                typeBelow=1;
                IniField.LOGGER.debug("gen furry");
            }
        }

        world.setBlock(pos.above(),this.defaultBlockState().setValue(WATERLOGGED,waterLogged).setValue(TYPE,type).setValue(AGE,0),3);
        world.setBlock(pos,this.defaultBlockState().setValue(TYPE,typeBelow).setValue(AGE,0),3);


        IniField.LOGGER.debug("gen: "+"waterlogged: "+waterLogged+" type: "+type);
        IniField.LOGGER.debug("below: "+" type: "+typeBelow);

        spawnWeedFurryParticles(world,pos,blockState);

    }

    //disc, about bonemeal
    public float getDestroyProgress(BlockState p_180647_1_, PlayerEntity p_180647_2_, IBlockReader p_180647_3_, BlockPos p_180647_4_) {
        return p_180647_2_.getMainHandItem().getItem() instanceof SwordItem ? 1.0F : super.getDestroyProgress(p_180647_1_, p_180647_2_, p_180647_3_, p_180647_4_);
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if ((Boolean)p_196271_1_.getValue(WATERLOGGED)) {
            p_196271_4_.getLiquidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickDelay(p_196271_4_));
        }
        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);

    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        BlockPos topPos=this.getTopPos(iBlockReader,blockPos);
        return (iBlockReader.getBlockState(topPos.above()).isAir()||iBlockReader.getBlockState(topPos.above()).getFluidState().is(FluidTags.WATER))
                &&(getPlantHeight(iBlockReader,blockPos)<8);
    }

    @Override
    public boolean isBonemealSuccess(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {
        this.growWeed(blockState,serverWorld,blockPos);
    }

    private boolean onProperBlock(Block block){
        return block==Blocks.DIRT||block==Blocks.GRASS_BLOCK||block==Blocks.SAND||block==Blocks.COARSE_DIRT||block==Blocks.PODZOL||block==Blocks.GRAVEL;
    }
}
