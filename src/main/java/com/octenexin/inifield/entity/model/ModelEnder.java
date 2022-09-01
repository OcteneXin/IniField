package com.octenexin.inifield.entity.model;

// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.octenexin.inifield.entity.Ender;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEnder extends EntityModel<Ender> {
    private final ModelRenderer arm_l;
    private final ModelRenderer arm_r;
    private final ModelRenderer leg_l;
    private final ModelRenderer leg_r;
    private final ModelRenderer cloth;
    private final ModelRenderer neck;
    private final ModelRenderer head;
    private final ModelRenderer hair;

    public ModelEnder() {
        texWidth = 256;
        texHeight = 256;

        arm_l = new ModelRenderer(this);
        arm_l.setPos(9.5F, -52.5F, -0.5F);
        setRotationAngle(arm_l, 0.0F, 0.0F, -0.0436F);
        arm_l.texOffs(79, 105).addBox(-0.25F, 32.0F, -3.25F, 7.0F, 4.0F, 7.0F, 0.0F, false);
        arm_l.texOffs(0, 72).addBox(0.0F, 0.0F, -3.0F, 6.0F, 36.0F, 6.0F, 0.0F, false);
        arm_l.texOffs(107, 109).addBox(0.5F, 35.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.0F, false);

        arm_r = new ModelRenderer(this);
        arm_r.setPos(-9.5F, -52.5F, -0.5F);
        setRotationAngle(arm_r, 0.0F, 0.0F, 0.0436F);
        arm_r.texOffs(103, 98).addBox(-6.75F, 32.0F, -3.25F, 7.0F, 4.0F, 7.0F, 0.0F, false);
        arm_r.texOffs(64, 57).addBox(-6.0F, 0.0F, -3.0F, 6.0F, 36.0F, 6.0F, 0.0F, false);
        arm_r.texOffs(109, 49).addBox(-5.5F, 35.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.0F, false);

        leg_l = new ModelRenderer(this);
        leg_l.setPos(6.0F, -13.0F, 0.0F);
        leg_l.texOffs(18, 109).addBox(-3.5F, 30.0F, -3.5F, 6.0F, 4.0F, 6.0F, 0.0F, false);
        leg_l.texOffs(48, 2).addBox(-3.0F, 33.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        leg_l.texOffs(80, 94).addBox(-4.0F, 34.0F, -5.0F, 7.0F, 3.0F, 8.0F, 0.0F, false);
        leg_l.texOffs(24, 72).addBox(-4.0F, 0.0F, -4.0F, 7.0F, 30.0F, 7.0F, 0.0F, false);

        leg_r = new ModelRenderer(this);
        leg_r.setPos(-6.0F, -13.0F, 0.0F);
        leg_r.texOffs(99, 18).addBox(-3.0F, 34.0F, -5.0F, 7.0F, 3.0F, 8.0F, 0.0F, false);
        leg_r.texOffs(109, 0).addBox(-2.5F, 30.0F, -3.5F, 6.0F, 4.0F, 6.0F, 0.0F, false);
        leg_r.texOffs(48, 4).addBox(-2.0F, 33.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        leg_r.texOffs(88, 57).addBox(-3.0F, 0.0F, -4.0F, 7.0F, 30.0F, 7.0F, 0.0F, false);

        cloth = new ModelRenderer(this);
        cloth.setPos(0.0F, 24.0F, 0.0F);
        cloth.texOffs(58, 99).addBox(-9.0F, -77.0F, -6.0F, 3.0F, 1.0F, 11.0F, 0.0F, false);
        cloth.texOffs(41, 98).addBox(6.0F, -77.0F, -6.0F, 3.0F, 1.0F, 11.0F, 0.0F, false);
        cloth.texOffs(109, 61).addBox(-6.0F, -77.0F, 3.0F, 12.0F, 1.0F, 2.0F, 0.0F, false);
        cloth.texOffs(94, 39).addBox(-8.0F, -76.0F, 4.0F, 16.0F, 1.0F, 1.0F, 0.0F, false);
        cloth.texOffs(102, 94).addBox(-7.0F, -75.0F, 4.0F, 14.0F, 2.0F, 1.0F, 0.0F, false);
        cloth.texOffs(36, 110).addBox(-5.0F, -73.0F, 4.0F, 10.0F, 2.0F, 1.0F, 0.0F, false);
        cloth.texOffs(48, 0).addBox(-4.5F, -71.0F, 4.0F, 9.0F, 1.0F, 1.0F, 0.0F, false);
        cloth.texOffs(56, 72).addBox(5.5F, -73.0F, -5.75F, 1.0F, 14.0F, 1.0F, 0.0F, false);
        cloth.texOffs(52, 72).addBox(-6.5F, -73.0F, -5.75F, 1.0F, 14.0F, 1.0F, 0.0F, false);
        cloth.texOffs(0, 5).addBox(-6.5F, -59.0F, -6.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cloth.texOffs(0, 0).addBox(5.5F, -59.0F, -6.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cloth.texOffs(0, 5).addBox(-6.0F, -77.0F, -6.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        cloth.texOffs(0, 0).addBox(5.0F, -77.0F, -6.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        cloth.texOffs(0, 53).addBox(-8.0F, -76.0F, -6.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        cloth.texOffs(0, 51).addBox(4.0F, -76.0F, -6.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        cloth.texOffs(0, 48).addBox(2.0F, -75.0F, -6.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        cloth.texOffs(0, 45).addBox(-7.0F, -75.0F, -6.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        cloth.texOffs(48, 8).addBox(-5.0F, -73.0F, -6.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        cloth.texOffs(0, 43).addBox(-3.0F, -72.0F, -6.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        cloth.texOffs(48, 6).addBox(1.0F, -73.0F, -6.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        cloth.texOffs(58, 0).addBox(-10.0F, -43.5F, -6.0F, 20.0F, 7.0F, 11.0F, 0.0F, false);
        cloth.texOffs(0, 0).addBox(-9.5F, -76.5F, -5.5F, 19.0F, 33.0F, 10.0F, 0.0F, false);

        neck = new ModelRenderer(this);
        neck.setPos(0.0F, 24.0F, 0.0F);
        neck.texOffs(94, 31).addBox(-5.0F, -77.0F, -4.0F, 10.0F, 1.0F, 7.0F, 0.0F, false);
        neck.texOffs(106, 41).addBox(-4.0F, -79.0F, -3.5F, 8.0F, 2.0F, 6.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, -55.0F, 0.0F);
        head.texOffs(50, 31).addBox(-8.0F, -14.0F, -7.0F, 16.0F, 14.0F, 12.0F, 0.0F, false);

        hair = new ModelRenderer(this);
        hair.setPos(0.0F, 79.0F, 0.0F);
        head.addChild(hair);
        hair.texOffs(58, 18).addBox(-7.5F, -99.0F, -6.5F, 15.0F, 2.0F, 11.0F, 0.0F, false);
        hair.texOffs(0, 43).addBox(-9.0F, -97.0F, -8.0F, 18.0F, 15.0F, 14.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Ender entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

        matrixStack.pushPose();
        matrixStack.scale(0.5f,0.5f,0.5f);
        matrixStack.translate(0,1.5,0);

        arm_l.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        arm_r.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_l.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leg_r.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        cloth.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        matrixStack.popPose();
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}