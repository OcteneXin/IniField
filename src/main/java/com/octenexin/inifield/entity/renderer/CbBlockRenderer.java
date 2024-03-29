package com.octenexin.inifield.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.octenexin.inifield.IniField;
import com.octenexin.inifield.entity.CbBlockEntity;
import com.octenexin.inifield.entity.model.ModelCbBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class CbBlockRenderer extends EntityRenderer<CbBlockEntity> {
    private static final ResourceLocation LOCATION = IniField.locate("textures/entity/cb_block.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(LOCATION);
    private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));
    private final ModelCbBlock model = new ModelCbBlock();

    public CbBlockRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(CbBlockEntity p_110775_1_) {
        return LOCATION;
    }

    public static float getY(CbBlockEntity p_229051_0_, float p_229051_1_) {
        float f = (float)p_229051_0_.time + p_229051_1_;
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = (f1 * f1 + f1) * 0.4F;
        return f1 - 1.4F;
    }

    @Override
    public void render(CbBlockEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        float f = getY(p_225623_1_, p_225623_3_);
        float f1 = ((float)p_225623_1_.time + p_225623_3_) * 3.0F;
        IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(RENDER_TYPE);
        p_225623_4_.pushPose();
        p_225623_4_.scale(0.5F, 0.5F, 0.5F);
        p_225623_4_.translate(0.0D, 0.25D, 0.0D);
        int i = OverlayTexture.NO_OVERLAY;


        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(f1));
        p_225623_4_.translate(0.0D, (double)(1.5F + f / 2.0F), 0.0D);
        p_225623_4_.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
        this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, i, 1.0F, 1.0F, 1.0F, 1.0F);

        p_225623_4_.popPose();
        p_225623_4_.popPose();


        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
}
