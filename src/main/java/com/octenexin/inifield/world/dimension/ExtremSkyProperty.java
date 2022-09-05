package com.octenexin.inifield.world.dimension;

import com.octenexin.inifield.client.AetherSkyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;

// CLIENT-SIDED
public class ExtremSkyProperty extends DimensionRenderInfo {

    float worldTime;
    private ISkyRenderHandler skyRenderer;
    private Minecraft mc;

    public ExtremSkyProperty() {
        super(0.0F, false, FogType.NORMAL, false, false);
        mc=Minecraft.getInstance();
    }

    public Vector3d getBrightnessDependentFogColor(Vector3d p_230494_1_, float p_230494_2_) {
        worldTime=p_230494_2_;
        return p_230494_1_.multiply((double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.94F + 0.06F), (double)(p_230494_2_ * 0.91F + 0.09F));
    }

    public boolean isFoggyAt(int p_230493_1_, int p_230493_2_) {
        return true;
    }

    @Nullable
    public ISkyRenderHandler getSkyRenderHandler() {
        if (skyRenderer == null)
            skyRenderer = new AetherSkyRenderer();
        return skyRenderer;
    }

}
