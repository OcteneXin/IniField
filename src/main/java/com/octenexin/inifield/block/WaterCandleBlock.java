package com.octenexin.inifield.block;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.client.particles.WeedFurryParticleData;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class WaterCandleBlock extends BushBlock implements IWaterLoggable, IGrowable {

    //handle block state
    private static final IntegerProperty WEED_TYPE=IntegerProperty.create("weed_type", 0, 3);
    protected static final VoxelShape SHAPE = Block.box(1D, 0.0D, 1D, 15D, 14.0D, 15D);
    public static final IntegerProperty AGE;//0:can grow,1:keep height
    public static final IntegerProperty TYPE;//0:small,1:normal,2:tall,3:big
    public static final BooleanProperty WATERLOGGED;//0:above water,1:below water

    private static final IParticleData FURRY_PARTICLE=new WeedFurryParticleData(255,255,255);

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AGE, TYPE, WATERLOGGED);
    }



    static {
        AGE = BlockStateProperties.AGE_1;//age
        TYPE= WEED_TYPE;//weed_type
        WATERLOGGED= BlockStateProperties.WATERLOGGED;
    }

    public WaterCandleBlock(Properties p_i48780_1_) {
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

    //handle put and die

    //proper block&&water||this below, survive
    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        BlockPos blockpos = p_196260_3_.below();
        BlockState blockstate = p_196260_2_.getBlockState(blockpos);
        return onProperBlock(blockstate.getBlock())&&((p_196260_2_.isWaterAt(p_196260_3_)&&(!(p_196260_2_.isWaterAt(p_196260_3_.above()))))||(!p_196260_2_.isWaterAt(p_196260_3_)&&hasWaterAround(p_196260_2_,blockpos)));
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

    @Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {

        if(p_225542_4_.nextInt(5)==0){
            p_225542_1_.setValue(AGE,1);
            return;
        }

        if (p_225542_1_.getValue(TYPE)!=3) {
            if(p_225542_4_.nextInt(10)==3){
                if (p_225542_2_.getRawBrightness(p_225542_3_.above(), 0) >= 9) {
                    p_225542_1_.setValue(TYPE,p_225542_1_.getValue(TYPE)+1);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        if(p_180655_1_.getValue(TYPE)==3&&p_180655_4_.nextDouble()<0.15D){
            double d0 = (double)p_180655_3_.getX() + 0.5D;
            double d1 = (double)p_180655_3_.getY() + 0.7D;
            double d2 = (double)p_180655_3_.getZ() + 0.5D;
            //p_180655_2_.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            p_180655_2_.addParticle(FURRY_PARTICLE, d0, d1, d2, p_180655_4_.nextDouble()*0.3D-0.15D, p_180655_4_.nextDouble()*0.1D, p_180655_4_.nextDouble()*0.3D-0.15D);
        }

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
        return blockState.getValue(TYPE)<3;
    }

    @Override
    public boolean isBonemealSuccess(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {

        int newType=blockState.getValue(TYPE) + 1;

        serverWorld.setBlock(blockPos,blockState.setValue(TYPE, Math.min(newType, 3)), 2);
    }

    private boolean onProperBlock(Block block){
        return block==Blocks.DIRT||block==Blocks.GRASS_BLOCK||block==Blocks.SAND||block==Blocks.COARSE_DIRT||block==Blocks.PODZOL||block==Blocks.GRAVEL;
    }

    private boolean hasWaterAround(IWorldReader world, BlockPos pos){
        return world.isWaterAt(pos.east())||world.isWaterAt(pos.west())||world.isWaterAt(pos.south())||world.isWaterAt(pos.north());
    }
}
