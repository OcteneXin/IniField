// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.octenexin.inifield.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.octenexin.inifield.entity.Notch;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class ModelNotch extends EntityModel<Notch> {
	private final ModelRenderer coat;
	private final ModelRenderer cube_r1;
	private final ModelRenderer behind_cloth_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer right_cloth_r1;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	private final ModelRenderer cube_r11;
	private final ModelRenderer cube_r12;
	private final ModelRenderer cube_r13;
	private final ModelRenderer cube_r14;
	private final ModelRenderer cube_r15;
	private final ModelRenderer cube_r16;
	private final ModelRenderer cube_r17;
	private final ModelRenderer cube_r18;
	private final ModelRenderer cube_r19;
	private final ModelRenderer cube_r20;
	private final ModelRenderer cube_r21;
	private final ModelRenderer left_cloth_r1;
	private final ModelRenderer left_cloth_down;
	private final ModelRenderer cube_r22;
	private final ModelRenderer cube_r23;
	private final ModelRenderer right_cloth_down;
	private final ModelRenderer cube_r24;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftLeg;
	private final ModelRenderer rightLeg;
	private final ModelRenderer head;
	private final ModelRenderer leftArm2;
	private final ModelRenderer rightArm2;
	private final ModelRenderer t_shirt;
	private final ModelRenderer bb_main;
	private final ModelRenderer cape_r1;

	public ModelNotch() {
		texWidth = 256;
		texHeight = 256;

		coat = new ModelRenderer(this);
		coat.setPos(0.0F, -8.0F, 0.0F);
		coat.texOffs(60, 0).addBox(-8.5F, 3.5F, -6.0F, 1.0F, 2.0F, 14.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -15.0F, 5.0F);
		coat.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0873F, 0.0F, 0.0F);
		cube_r1.texOffs(94, 36).addBox(-8.0F, 18.0F, 1.0F, 16.0F, 2.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(94, 36).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 21.0F, 1.0F, 0.0F, false);

		behind_cloth_r1 = new ModelRenderer(this);
		behind_cloth_r1.setPos(0.0F, 6.0F, 6.0F);
		coat.addChild(behind_cloth_r1);
		setRotationAngle(behind_cloth_r1, 0.3054F, 0.0F, 0.0F);
		behind_cloth_r1.texOffs(109, 113).addBox(-8.0F, 0.0F, 1.0F, 16.0F, 16.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(8.0F, -15.0F, 1.0F);
		coat.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.0436F);
		cube_r2.texOffs(25, 81).addBox(0.0F, -1.0F, -5.0F, 1.0F, 22.0F, 10.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(8.0F, 6.0F, 0.0F);
		coat.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -0.0436F, 0.0F);
		cube_r3.texOffs(111, 11).addBox(0.0F, -3.0F, -6.0F, 1.0F, 2.0F, 14.0F, 0.0F, false);

		right_cloth_r1 = new ModelRenderer(this);
		right_cloth_r1.setPos(-8.0F, 6.0F, 1.0F);
		coat.addChild(right_cloth_r1);
		setRotationAngle(right_cloth_r1, 0.0F, 0.0F, 0.2182F);
		right_cloth_r1.texOffs(65, 112).addBox(-1.0F, -1.0F, -5.0F, 1.0F, 16.0F, 11.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-9.0F, -15.0F, 0.0F);
		coat.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, -0.0436F);
		cube_r4.texOffs(0, 81).addBox(0.0F, -1.0F, -4.0F, 1.0F, 22.0F, 10.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(8.0F, -15.0F, 2.0F);
		coat.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, 0.5236F);
		cube_r5.texOffs(48, 128).addBox(0.0F, -4.0F, -6.0F, 1.0F, 4.0F, 11.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(-8.0F, -15.0F, 2.0F);
		coat.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 0.0F, -0.48F);
		cube_r6.texOffs(127, 47).addBox(-1.0F, -4.0F, -6.0F, 1.0F, 4.0F, 11.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setPos(0.0F, -15.0F, 6.0F);
		coat.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.5672F, 0.0F, 0.0F);
		cube_r7.texOffs(114, 79).addBox(-8.0F, -4.0F, 0.0F, 16.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setPos(-8.0F, -15.0F, 6.0F);
		coat.addChild(cube_r8);
		setRotationAngle(cube_r8, -0.4363F, -0.829F, 0.0F);
		cube_r8.texOffs(46, 63).addBox(-0.5F, -4.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r9 = new ModelRenderer(this);
		cube_r9.setPos(8.0F, -15.0F, 6.0F);
		coat.addChild(cube_r9);
		setRotationAngle(cube_r9, -0.4363F, 0.829F, 0.0F);
		cube_r9.texOffs(6, 63).addBox(-1.5F, -4.0F, -0.5F, 2.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r10 = new ModelRenderer(this);
		cube_r10.setPos(-9.0F, -14.0F, -4.0F);
		coat.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 0.2182F, 0.0F);
		cube_r10.texOffs(33, 140).addBox(0.0F, 0.0F, -1.0F, 4.0F, 20.0F, 1.0F, 0.0F, false);

		cube_r11 = new ModelRenderer(this);
		cube_r11.setPos(9.0F, -14.0F, -4.0F);
		coat.addChild(cube_r11);
		setRotationAngle(cube_r11, -0.0097F, -0.218F, 0.0447F);
		cube_r11.texOffs(72, 139).addBox(-4.0F, 0.0F, -1.0F, 4.0F, 20.0F, 1.0F, 0.0F, false);

		cube_r12 = new ModelRenderer(this);
		cube_r12.setPos(-9.0F, -14.0F, -4.0F);
		coat.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.3927F, 0.2182F, 0.0F);
		cube_r12.texOffs(0, 51).addBox(0.0F, -4.0F, -1.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r13 = new ModelRenderer(this);
		cube_r13.setPos(9.0F, -14.0F, -4.0F);
		coat.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.3927F, -0.2182F, 0.0F);
		cube_r13.texOffs(0, 18).addBox(-3.0F, -4.0F, -1.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r14 = new ModelRenderer(this);
		cube_r14.setPos(-9.0F, -14.0F, -4.0F);
		coat.addChild(cube_r14);
		setRotationAngle(cube_r14, 2.4097F, 1.3759F, 2.1911F);
		cube_r14.texOffs(0, 63).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r15 = new ModelRenderer(this);
		cube_r15.setPos(8.0F, -14.0F, -4.0F);
		coat.addChild(cube_r15);
		setRotationAngle(cube_r15, 2.2023F, -1.3072F, -1.8843F);
		cube_r15.texOffs(24, 51).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);

		cube_r16 = new ModelRenderer(this);
		cube_r16.setPos(-3.0F, 6.0F, -4.0F);
		coat.addChild(cube_r16);
		setRotationAngle(cube_r16, 0.0143F, 0.222F, -0.1341F);
		cube_r16.texOffs(23, 140).addBox(-1.8578F, -20.0F, -2.2986F, 4.0F, 20.0F, 1.0F, 0.0F, false);

		cube_r17 = new ModelRenderer(this);
		cube_r17.setPos(5.0F, 6.0F, -5.0F);
		coat.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.0143F, -0.222F, 0.1341F);
		cube_r17.texOffs(78, 98).addBox(-3.7799F, -20.0F, -1.0245F, 4.0F, 20.0F, 1.0F, 0.0F, false);

		cube_r18 = new ModelRenderer(this);
		cube_r18.setPos(-3.0F, 6.0F, -4.0F);
		coat.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.0436F, 0.2182F, 0.0F);
		cube_r18.texOffs(60, 0).addBox(0.1422F, -11.0F, -2.29F, 2.0F, 11.0F, 1.0F, 0.0F, false);

		cube_r19 = new ModelRenderer(this);
		cube_r19.setPos(3.0F, 6.0F, -6.0F);
		coat.addChild(cube_r19);
		setRotationAngle(cube_r19, 0.0436F, -0.2182F, 0.0F);
		cube_r19.texOffs(32, 51).addBox(-1.7F, -11.0F, -0.2986F, 2.0F, 11.0F, 1.0F, 0.0F, false);

		cube_r20 = new ModelRenderer(this);
		cube_r20.setPos(-5.0F, 6.0F, -4.0F);
		coat.addChild(cube_r20);
		setRotationAngle(cube_r20, -0.0598F, 0.2271F, 0.1145F);
		cube_r20.texOffs(41, 24).addBox(-4.0F, -2.0F, -2.8658F, 8.0F, 2.0F, 1.0F, 0.0F, false);

		cube_r21 = new ModelRenderer(this);
		cube_r21.setPos(10.0F, 5.0F, -4.0F);
		coat.addChild(cube_r21);
		setRotationAngle(cube_r21, -0.0598F, -0.2271F, -0.1145F);
		cube_r21.texOffs(50, 36).addBox(-9.0F, -2.0F, -1.8658F, 8.0F, 2.0F, 1.0F, 0.0F, false);

		left_cloth_r1 = new ModelRenderer(this);
		left_cloth_r1.setPos(8.0F, 6.0F, 3.0F);
		coat.addChild(left_cloth_r1);
		setRotationAngle(left_cloth_r1, 0.0F, 0.0F, -0.2182F);
		left_cloth_r1.texOffs(24, 113).addBox(0.0F, -1.0F, -7.0F, 1.0F, 16.0F, 11.0F, 0.0F, false);

		left_cloth_down = new ModelRenderer(this);
		left_cloth_down.setPos(5.0F, 5.0F, -4.0F);
		coat.addChild(left_cloth_down);


		cube_r22 = new ModelRenderer(this);
		cube_r22.setPos(5.0F, 0.0F, 0.0F);
		left_cloth_down.addChild(cube_r22);
		setRotationAngle(cube_r22, -0.0598F, -0.2271F, -0.1145F);
		cube_r22.texOffs(0, 7).addBox(-7.0F, 3.0F, -2.0F, 5.0F, 6.0F, 1.0F, 0.0F, false);

		cube_r23 = new ModelRenderer(this);
		cube_r23.setPos(0.0F, 0.0F, 0.0F);
		left_cloth_down.addChild(cube_r23);
		setRotationAngle(cube_r23, -0.0598F, -0.2271F, -0.1145F);
		cube_r23.texOffs(113, 130).addBox(-4.1603F, 0.6372F, -1.948F, 8.0F, 17.0F, 1.0F, 0.0F, false);

		right_cloth_down = new ModelRenderer(this);
		right_cloth_down.setPos(-5.0F, 6.0F, -4.0F);
		coat.addChild(right_cloth_down);


		cube_r24 = new ModelRenderer(this);
		cube_r24.setPos(0.0F, 0.0F, 0.0F);
		right_cloth_down.addChild(cube_r24);
		setRotationAngle(cube_r24, -0.0598F, 0.2271F, 0.1145F);
		cube_r24.texOffs(131, 130).addBox(-3.9052F, -1.0F, -1.8658F, 8.0F, 17.0F, 1.0F, 0.0F, false);
		cube_r24.texOffs(0, 0).addBox(-2.9052F, 3.0F, -2.8658F, 5.0F, 6.0F, 1.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(9.0F, -22.0F, 2.0F);
		setRotationAngle(leftArm, 0.0F, 0.0F, -0.0873F);
		leftArm.texOffs(109, 85).addBox(-0.5F, 0.0F, -2.5F, 6.0F, 22.0F, 6.0F, 0.0F, false);
		leftArm.texOffs(94, 58).addBox(0.0F, 22.0F, -2.0F, 5.0F, 7.0F, 5.0F, 0.0F, false);
		leftArm.texOffs(128, 35).addBox(-1.0F, 18.0F, -3.0F, 7.0F, 2.0F, 7.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-8.0F, -22.0F, 3.0F);
		setRotationAngle(rightArm, 0.0F, 0.0F, 0.0873F);
		rightArm.texOffs(109, 85).addBox(-6.5F, 0.0F, -3.5F, 6.0F, 22.0F, 6.0F, 0.0F, false);
		rightArm.texOffs(127, 12).addBox(-7.5F, 18.0F, -4.0F, 7.0F, 2.0F, 7.0F, 0.0F, false);
		rightArm.texOffs(89, 93).addBox(-6.0F, 1.0F, -3.0F, 5.0F, 28.0F, 5.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(5.0F, 0.0F, 0.0F);
		leftLeg.texOffs(97, 0).addBox(-4.0F, 0.0F, -3.0F, 7.0F, 18.0F, 7.0F, 0.0F, false);
		leftLeg.texOffs(133, 84).addBox(-3.5F, 18.0F, -2.5F, 6.0F, 2.0F, 6.0F, 0.0F, false);
		leftLeg.texOffs(0, 51).addBox(-4.5F, 20.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-5.0F, 0.0F, 0.0F);
		rightLeg.texOffs(48, 98).addBox(-3.0F, 0.0F, -3.0F, 7.0F, 18.0F, 7.0F, 0.0F, false);
		rightLeg.texOffs(133, 92).addBox(-2.5F, 18.0F, -2.5F, 6.0F, 2.0F, 6.0F, 0.0F, false);
		rightLeg.texOffs(127, 0).addBox(-3.5F, 20.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -27.0F, 0.0F);
		head.texOffs(38, 39).addBox(-8.0F, -12.0F, -5.0F, 16.0F, 12.0F, 12.0F, 0.0F, false);
		head.texOffs(50, 18).addBox(-8.5F, -11.0F, -5.5F, 17.0F, 5.0F, 13.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-11.0F, -13.0F, -7.0F, 22.0F, 2.0F, 16.0F, 0.0F, false);
		head.texOffs(0, 63).addBox(-8.0F, -17.0F, -6.0F, 16.0F, 4.0F, 14.0F, 0.0F, false);

		leftArm2 = new ModelRenderer(this);
		leftArm2.setPos(9.0F, -24.0F, 2.0F);
		setRotationAngle(leftArm2, 0.0F, 0.0F, -0.0873F);
		leftArm2.texOffs(0, 113).addBox(-1.0F, 0.0F, -2.5F, 6.0F, 10.0F, 6.0F, 0.0F, false);
		leftArm2.texOffs(94, 58).addBox(0.0F, 10.0F, -2.0F, 5.0F, 19.0F, 5.0F, 0.0F, false);

		rightArm2 = new ModelRenderer(this);
		rightArm2.setPos(-8.0F, -24.0F, 3.0F);
		setRotationAngle(rightArm2, 0.0F, 0.0F, 0.0873F);
		rightArm2.texOffs(0, 113).addBox(-6.0F, 0.0F, -3.5F, 6.0F, 10.0F, 6.0F, 0.0F, false);
		rightArm2.texOffs(89, 93).addBox(-5.0F, 10.0F, -3.0F, 5.0F, 19.0F, 5.0F, 0.0F, false);

		t_shirt = new ModelRenderer(this);
		t_shirt.setPos(0.0F, 24.0F, 0.0F);
		t_shirt.texOffs(0, 182).addBox(3.0F, -24.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		t_shirt.texOffs(0, 182).addBox(7.0F, -22.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		t_shirt.texOffs(0, 182).addBox(5.0F, -23.0F, -4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
		t_shirt.texOffs(0, 18).addBox(-8.0F, -48.0F, -4.0F, 16.0F, 24.0F, 9.0F, 0.0F, false);
		t_shirt.texOffs(0, 162).addBox(-5.0F, -51.0F, -2.0F, 10.0F, 2.0F, 6.0F, 0.0F, false);
		t_shirt.texOffs(0, 173).addBox(-6.0F, -49.0F, -3.0F, 12.0F, 1.0F, 7.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);


		cape_r1 = new ModelRenderer(this);
		cape_r1.setPos(0.0F, -48.0F, 7.0F);
		bb_main.addChild(cape_r1);
		setRotationAngle(cape_r1, 0.3927F, 0.0F, 0.0F);
		cape_r1.texOffs(0, 186).addBox(-9.0F, 1.0F, -2.0F, 18.0F, 34.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Notch p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		boolean flag = p_225597_1_.getFallFlyingTicks() > 4;

		this.head.yRot = p_225597_5_ * ((float)Math.PI / 180F);
		//this.head.xRot = p_225597_6_ * ((float)Math.PI / 180F);

		this.rightArm.z = 0.0F;
		this.rightArm.x = -5.0F;
		this.leftArm.z = 0.0F;
		this.leftArm.x = 5.0F;
		float f = 1.0F;
		if (flag) {
			f = (float)p_225597_1_.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.rightArm.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 2.0F * p_225597_3_ * 0.5F / f;
		this.leftArm.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 2.0F * p_225597_3_ * 0.5F / f;
		this.rightArm.zRot = 0.0873F;
		this.leftArm.zRot = -0.0873F;
		this.rightLeg.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_ / f;
		this.rightLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;

		this.leftLeg.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_ / f;
		this.leftLeg.yRot = 0.0F;
		this.leftLeg.zRot = 0.0F;

		this.left_cloth_down.xRot = Math.min(0,MathHelper.cos(p_225597_2_*0.6662F)*1.2F*p_225597_3_/f);
		this.left_cloth_down.yRot = 0.0F;
		this.left_cloth_down.zRot = 0.0F;

		this.right_cloth_down.xRot = Math.min(0,MathHelper.cos(p_225597_2_*0.6662F)*1.2F*p_225597_3_/f);
		this.right_cloth_down.yRot = 0.0F;
		this.right_cloth_down.zRot = 0.0F;

		this.cape_r1.xRot=0.3927F+Math.max(0,MathHelper.cos(p_225597_2_*0.6662F)*0.8F*p_225597_3_/f);
		this.cape_r1.yRot=0.0F;
		this.cape_r1.zRot=0.0F;

		this.behind_cloth_r1.xRot=0.3054F+Math.max(0,MathHelper.cos(p_225597_2_*0.6662F)*0.5F*p_225597_3_/f);
		this.behind_cloth_r1.yRot=0.0F;
		this.behind_cloth_r1.zRot=0.0F;

		if (this.riding) {
			this.rightArm.xRot += (-(float)Math.PI / 5F);
			this.leftArm.xRot += (-(float)Math.PI / 5F);
			this.rightLeg.xRot = -1.4137167F;
			this.rightLeg.yRot = ((float)Math.PI / 10F);
			this.rightLeg.zRot = 0.07853982F;
			this.leftLeg.xRot = -1.4137167F;
			this.leftLeg.yRot = (-(float)Math.PI / 10F);
			this.leftLeg.zRot = -0.07853982F;
		}

		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;


		//this.hat.copyFrom(this.head);
	}

	protected float rotlerpRad(float p_205060_1_, float p_205060_2_, float p_205060_3_) {
		float f = (p_205060_3_ - p_205060_2_) % ((float)Math.PI * 2F);
		if (f < -(float)Math.PI) {
			f += ((float)Math.PI * 2F);
		}

		if (f >= (float)Math.PI) {
			f -= ((float)Math.PI * 2F);
		}

		return p_205060_2_ + p_205060_1_ * f;
	}


	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

		matrixStack.pushPose();
		matrixStack.scale(0.5f,0.5f,0.5f);
		matrixStack.translate(0,1.5,0);

		coat.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		//leftArm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		//rightArm2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		t_shirt.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

		matrixStack.popPose();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}