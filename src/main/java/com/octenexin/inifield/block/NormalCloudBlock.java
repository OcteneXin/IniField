package com.octenexin.inifield.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

public class NormalCloudBlock extends Block {
    public NormalCloudBlock(AbstractBlock.Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    public VoxelShape getCollisionShape(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_) {
        return VoxelShapes.box(0.1D,0.1D,0.1D,0.9D,0.9D,0.9D);
    }

    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }

    @Override
    public void fallOn(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
        if (p_180658_3_.isSuppressingBounce()) {
            super.fallOn(p_180658_1_, p_180658_2_, p_180658_3_, p_180658_4_);
        } else {
            p_180658_3_.causeFallDamage(p_180658_4_, 0.0F);
        }
    }

    public void updateEntityAfterFallOn(IBlockReader p_176216_1_, Entity p_176216_2_) {
        if (p_176216_2_.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(p_176216_1_, p_176216_2_);
        } else {
            this.bounceUp(p_176216_2_);
        }

    }

    private void bounceUp(Entity p_226946_1_) {
        Vector3d vector3d = p_226946_1_.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = p_226946_1_ instanceof LivingEntity ? 1.0D : 0.8D;
            p_226946_1_.setDeltaMovement(vector3d.x, -vector3d.y * (double)0.66F * d0, vector3d.z);
        }
    }

}
