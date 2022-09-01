package com.octenexin.inifield.entity.renderer;

import com.octenexin.inifield.entity.Ender;
import com.octenexin.inifield.entity.model.ModelEnder;
import com.octenexin.inifield.entity.model.ModelNotch;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class EnderRenderer extends LivingRenderer<Ender, ModelEnder> {
    public EnderRenderer(EntityRendererManager p_i50965_1_, ModelEnder p_i50965_2_, float p_i50965_3_) {
        super(p_i50965_1_, p_i50965_2_, p_i50965_3_);
    }

    public EnderRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelEnder(), 0.5F);

    }

    @Override
    public ResourceLocation getTextureLocation(Ender p_110775_1_) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/ender.png");
    }
}
