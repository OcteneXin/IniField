package com.octenexin.inifield.client.particles;

import com.mojang.serialization.Codec;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.SpriteMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class WeedFurryParticle extends SpriteTexturedParticle {

    public WeedFurryParticle(ClientWorld clientWorld, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
        super(clientWorld, xPos, yPos, zPos);
        this.xd += xSpeed;
        this.yd += ySpeed;
        this.zd += zSpeed;
        this.setSize(0.1F, 0.1F);
        this.gravity = -0.0005F * (this.random.nextFloat() * 0.5f + 0.5f);
        this.quadSize *= (this.random.nextFloat() * 0.5f + 0.63f);
        this.lifetime = 300 + this.random.nextInt(300);
        this.hasPhysics = true;

    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.98F;
            this.yd *= 0.98F;
            if(random.nextInt(10)==0){
                this.yd+=0.001F;
            }

            this.zd *= 0.98F;

            if (this.onGround&&this.yd<0) {
                this.yd*=-0.8F;
            }
        }

    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

/*    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite sprite) {
            this.sprites = sprite;
        }

        @Override
        public Particle createParticle(BasicParticleType particleType, ClientWorld clientWorld, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
            return new WeedFurryParticle(clientWorld, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed, sprites.get(clientWorld.random));
        }
    }*/


    public static class WeedParticleFactory implements IParticleFactory<WeedFurryParticleData> {
        private final IAnimatedSprite sprites;

        public WeedParticleFactory(IAnimatedSprite sprite) {
            this.sprites = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(WeedFurryParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            WeedFurryParticle particle = new WeedFurryParticle(worldIn, x, y, z, xSpeed,ySpeed,zSpeed);
            particle.pickSprite(sprites);
            return particle;
        }

    }

}
