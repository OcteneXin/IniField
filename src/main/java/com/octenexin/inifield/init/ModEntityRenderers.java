package com.octenexin.inifield.init;

import com.octenexin.inifield.entity.renderer.CbBlockRenderer;
import com.octenexin.inifield.entity.renderer.NotchRenderer;
import com.octenexin.inifield.entity.renderer.ThrowableTNTRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class ModEntityRenderers {

    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.NOTCH.get(), (EntityRendererManager manager) -> {
            return new NotchRenderer(manager);
        });


        RenderingRegistry.registerEntityRenderingHandler(ModEntities.THROWABLE_TNT.get(), (EntityRendererManager manager) -> {
            return new ThrowableTNTRenderer(manager);
        });

        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CB_BLOCK_ENTITY.get(), (EntityRendererManager manager) -> {
            return new CbBlockRenderer(manager);
        });
    }
}