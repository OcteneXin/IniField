package com.octenexin.inifield.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.octenexin.inifield.IniField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;

import java.util.Random;

public class ModSkyRenderer implements ISkyRenderHandler {

    private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation CLOUDS_LOCATION = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation FORCEFIELD_LOCATION = new ResourceLocation("textures/misc/forcefield.png");
    private static final ResourceLocation RAIN_LOCATION = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation SNOW_LOCATION = new ResourceLocation("textures/environment/snow.png");

    private final VertexFormat vertexBufferFormat = DefaultVertexFormats.POSITION;
    private VertexBuffer starVBO;
    private final TextureManager textureManager=Minecraft.getInstance().getTextureManager();

    public ModSkyRenderer(){
        generateStars();
    }

    // [VanillaCopy] WorldRenderer.renderSky's overworld branch, using our own stars at full brightness
    // Copy of TF
    @Override
    public void render(int ticks, float p_228424_2_, MatrixStack p_228424_1_, ClientWorld world, Minecraft mc) {

        WorldRenderer rg = mc.levelRenderer;

        //sky
        RenderSystem.disableTexture();
        Vector3d vector3d = world.getSkyColor(mc.gameRenderer.getMainCamera().getBlockPosition(), p_228424_2_);
        float f = (float)vector3d.x;
        float f1 = (float)vector3d.y;
        float f2 = (float)vector3d.z;
        FogRenderer.levelFogColor();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        RenderSystem.depthMask(false);
        RenderSystem.enableFog();
        RenderSystem.color3f(f, f1, f2);
        rg.skyBuffer.bind();
        this.vertexBufferFormat.setupBufferState(0L);
        rg.skyBuffer.draw(p_228424_1_.last().pose(), 7);
        VertexBuffer.unbind();
        this.vertexBufferFormat.clearBufferState();
        RenderSystem.disableFog();
        RenderSystem.disableAlphaTest();

        //effect
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float[] afloat = mc.level.effects().getSunriseColor(mc.level.getTimeOfDay(p_228424_2_), p_228424_2_);
        if (afloat != null) {
            RenderSystem.disableTexture();
            RenderSystem.shadeModel(7425);
            p_228424_1_.pushPose();
            p_228424_1_.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            float f3 = MathHelper.sin(mc.level.getSunAngle(p_228424_2_)) < 0.0F ? 180.0F : 0.0F;
            p_228424_1_.mulPose(Vector3f.ZP.rotationDegrees(f3));
            p_228424_1_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            float f4 = afloat[0];
            float f5 = afloat[1];
            float f6 = afloat[2];
            Matrix4f matrix4f = p_228424_1_.last().pose();
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
            int i = 16;

            for(int j = 0; j <= 16; ++j) {
                float f7 = (float)j * ((float)Math.PI * 2F) / 16.0F;
                float f8 = MathHelper.sin(f7);
                float f9 = MathHelper.cos(f7);
                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
            }

            bufferbuilder.end();
            WorldVertexBufferUploader.end(bufferbuilder);
            p_228424_1_.popPose();
            RenderSystem.shadeModel(7424);
        }

        //rain,sun,moon
        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        p_228424_1_.pushPose();
        float f11 = 1.0F - mc.level.getRainLevel(p_228424_2_);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, f11);
        p_228424_1_.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        p_228424_1_.mulPose(Vector3f.XP.rotationDegrees(mc.level.getTimeOfDay(p_228424_2_) * 360.0F));
        Matrix4f matrix4f1 = p_228424_1_.last().pose();
        float f12 = 30.0F;
        this.textureManager.bind(SUN_LOCATION);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.end();
        WorldVertexBufferUploader.end(bufferbuilder);
        f12 = 20.0F;
        this.textureManager.bind(MOON_LOCATION);
        int k = mc.level.getMoonPhase();
        int l = k % 4;
        int i1 = k / 4 % 2;
        float f13 = (float)(l + 0) / 4.0F;
        float f14 = (float)(i1 + 0) / 2.0F;
        float f15 = (float)(l + 1) / 4.0F;
        float f16 = (float)(i1 + 1) / 2.0F;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
        bufferbuilder.end();
        WorldVertexBufferUploader.end(bufferbuilder);
        RenderSystem.disableTexture();

        //star, copy TF
        float f10 = this.getStarBrightness(p_228424_2_,mc.level) * f11;
        if (f10 > 0.0F) {
            RenderSystem.color4f(f10, f10, f10, f10);

            this.starVBO.bind();
            this.vertexBufferFormat.setupBufferState(0L);
            this.starVBO.draw(p_228424_1_.last().pose(), 7);
            VertexBuffer.unbind();
            this.vertexBufferFormat.clearBufferState();
        }


        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableFog();
        p_228424_1_.popPose();
        RenderSystem.color3f(0.0F, 0.0F, 0.0F);


        //ground, unused
        if (mc.level.effects().hasGround()) {
            RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
        } else {
            RenderSystem.color3f(f, f1, f2);
        }

        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        RenderSystem.disableFog();
    }

    // [VanillaCopy] RenderGlobal.generateStars
    private void generateStars() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();

        if (this.starVBO != null) {
            this.starVBO.close();
        }

        // TF - inlined RenderGlobal field that's only used once here
        this.starVBO = new VertexBuffer(DefaultVertexFormats.POSITION);
        this.renderStars(bufferbuilder);
        bufferbuilder.end();
        this.starVBO.upload(bufferbuilder);
    }

    // [VanillaCopy] of RenderGlobal.renderStars but with double the number of them
    @SuppressWarnings("unused")
    private void renderStars(BufferBuilder bufferBuilder) {
        Random random = new Random(10842L);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);

        // TF - 1500 -> 3000
        for (int i = 0; i < 3000; ++i) {
            double d0 = random.nextFloat() * 2.0F - 1.0F;
            double d1 = random.nextFloat() * 2.0F - 1.0F;
            double d2 = random.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.15F + random.nextFloat() * 0.1F;
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;

            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                d0 = d0 * d4;
                d1 = d1 * d4;
                d2 = d2 * d4;
                double d5 = d0 * 100.0D;
                double d6 = d1 * 100.0D;
                double d7 = d2 * 100.0D;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for (int j = 0; j < 4; ++j) {
                    double d17 = 0.0D;
                    double d18 = ((j & 2) - 1) * d3;
                    double d19 = ((j + 1 & 2) - 1) * d3;
                    double d20 = 0.0D;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0D * d13;
                    double d24 = 0.0D * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    bufferBuilder.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                }
            }
        }
    }

    private float getStarBrightness(float p_228330_1_,ClientWorld level) {
        float f = level.getTimeOfDay(p_228330_1_);
        if(f>=0.4F&&f<=0.6F){
            return 1.0F;
        }else if(f>0.25F&&f<0.4F){
            return f-0.25F;
        }
        else if(f>0.6F&&f<0.75F){
            return 0.75F-f;
        }else{
            return 0;
        }
    }
}
