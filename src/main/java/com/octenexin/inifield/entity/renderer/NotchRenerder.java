package com.octenexin.inifield.entity.renderer;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.entity.Notch;
import com.octenexin.inifield.entity.model.ModelNotch;
import com.octenexin.inifield.utils.Reference;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class NotchRenerder extends MobRenderer<Notch,ModelNotch>{

    public NotchRenerder(EntityRendererManager renderManagerIn, ModelNotch entityModelIn, float shadowSizeIn) {
        super(renderManagerIn, entityModelIn, shadowSizeIn);

    }
    public NotchRenerder(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelNotch(), 0.7F);

    }
    @Override
    public ResourceLocation getTextureLocation(Notch entity) {

        return new ResourceLocation(Reference.MOD_ID, "textures/entity/notch.png");
    }

}

