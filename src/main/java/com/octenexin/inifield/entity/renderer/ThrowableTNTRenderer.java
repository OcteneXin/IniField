package com.octenexin.inifield.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.octenexin.inifield.entity.ThrowableTNTEntity;
import com.octenexin.inifield.entity.model.ModelThTNT;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ThrowableTNTRenderer extends EntityRenderer<ThrowableTNTEntity> {

    private static final ResourceLocation LOCATION = new ResourceLocation(Reference.MOD_ID, "textures/entity/throwable_tnt.png");
    protected final ModelThTNT model=new ModelThTNT();

    public ThrowableTNTRenderer(EntityRendererManager p_i46190_1_) {
        super(p_i46190_1_);
        this.shadowRadius = 0.8F;
    }

    @Override
    public ResourceLocation getTextureLocation(ThrowableTNTEntity p_110775_1_) {
        return LOCATION;
    }

    public void render(ThrowableTNTEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        BlockState blockstate = Blocks.TNT.defaultBlockState();
        if (blockstate.getRenderShape() == BlockRenderType.MODEL) {
            World world = p_225623_1_.getLevel();
            if (blockstate != world.getBlockState(p_225623_1_.blockPosition()) && blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
                p_225623_4_.pushPose();
                BlockPos blockpos = new BlockPos(p_225623_1_.getX(), p_225623_1_.getBoundingBox().maxY, p_225623_1_.getZ());
                p_225623_4_.translate(-0.5D, 0.0D, -0.5D);
                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
                for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.chunkBufferLayers()) {
                    if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
                        net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
                        blockrendererdispatcher.getModelRenderer().tesselateBlock(world, blockrendererdispatcher.getBlockModel(blockstate), blockstate, blockpos, p_225623_4_, p_225623_5_.getBuffer(type), false, new Random(),1234, OverlayTexture.NO_OVERLAY);
                    }
                }
                net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
                p_225623_4_.popPose();
                super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
            }
        }
    }

}
