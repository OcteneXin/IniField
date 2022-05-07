// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

package com.octenexin.inifield.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.octenexin.inifield.entity.Notch;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelNotch extends BipedModel<Notch> {
	private final ModelRenderer cape;

	public ModelNotch() {
		super(0.0F,0.0F,64,64);

		cape = new ModelRenderer(this);
		cape.setPos(0.0F, 0.0F, 2.0F);
		setRotationAngle(cape, 0.0873F, 0.0F, 0.0F);
		cape.texOffs(0, 48).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 14.0F, 0.0F, 0.0F, false);

	}


	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(Notch p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		super.setupAnim(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
		if (p_225597_1_.isCrouching()) {
			this.cape.z = 1.4F;
			this.cape.y = 1.85F;
		} else {
			this.cape.z = 0.0F;
			this.cape.y = 0.0F;
		}
	}

	@Override
	public void setAllVisible(boolean p_178719_1_) {
		super.setAllVisible(p_178719_1_);
		this.cape.visible=p_178719_1_;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		cape.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}