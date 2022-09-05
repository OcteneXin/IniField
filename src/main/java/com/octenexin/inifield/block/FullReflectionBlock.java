package com.octenexin.inifield.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class FullReflectionBlock extends Block {
    public FullReflectionBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState p_200122_1_, BlockState p_200122_2_, Direction p_200122_3_) {
        return p_200122_2_.is(this) ? true : super.skipRendering(p_200122_1_, p_200122_2_, p_200122_3_);
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }

    @Override
    public void stepOn(World p_176199_1_, BlockPos p_176199_2_, Entity p_176199_3_) {
        if (p_176199_3_.xOld != p_176199_3_.getX() || p_176199_3_.zOld != p_176199_3_.getZ()){
            showParticles(p_176199_3_,2);
        }

        super.stepOn(p_176199_1_, p_176199_2_, p_176199_3_);
    }

    @OnlyIn(Dist.CLIENT)
    private static void showParticles(Entity p_226932_0_, int p_226932_1_) {
        Random r=new Random();

        if (r.nextInt(10)==0&&p_226932_0_.level.isClientSide) {

            for(int i = 0; i < p_226932_1_; ++i) {
                p_226932_0_.level.addParticle(ParticleTypes.PORTAL, p_226932_0_.getX(), p_226932_0_.getY(), p_226932_0_.getZ(), r.nextDouble()*0.1D-0.05D, r.nextDouble()*0.1D, r.nextDouble()*0.1D-0.05D);
            }

        }
    }


}
