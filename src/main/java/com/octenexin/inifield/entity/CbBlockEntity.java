package com.octenexin.inifield.entity;

import com.octenexin.inifield.IniField;
import com.octenexin.inifield.init.ModBlocks;
import com.octenexin.inifield.init.ModEntities;
import com.octenexin.inifield.init.ModItems;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.UUID;

public class CbBlockEntity extends MonsterEntity {

    public int time;
    public boolean isReturn;
    public boolean isCreative;
    private UUID ownerUUID;
    private int ownerNetworkId;
    private boolean leftOwner;
    private boolean dealtDamage;
    private boolean inGround;
    private int inGroundTime;
    public Vector3d deltaVector3d=Vector3d.ZERO;


    public CbBlockEntity(EntityType<? extends MonsterEntity> type, World worldIn){
        super(type, worldIn);
        this.time = this.random.nextInt(100000);
        this.getAttributes().getInstance(Attributes.MAX_HEALTH);
        this.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
        this.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
    }
    public CbBlockEntity(World p_i1699_1_, double p_i1699_2_, double p_i1699_4_, double p_i1699_6_) {
        this(ModEntities.CB_BLOCK_ENTITY.get(), p_i1699_1_);
        this.setPos(p_i1699_2_, p_i1699_4_, p_i1699_6_);
    }
    public CbBlockEntity(LivingEntity p_i48548_2_, World p_i48548_3_) {
        this(p_i48548_3_,p_i48548_2_.getX(), p_i48548_2_.getEyeY() - (double)0.1F, p_i48548_2_.getZ());
        this.setOwner(p_i48548_2_);
        IniField.LOGGER.debug("ownerNetworkId:"+this.ownerNetworkId+"---------------------------------------------");
        IniField.LOGGER.debug("ownerUUId:"+this.ownerUUID+"---------------------------------------------");

    }



    public void setOwner(@Nullable Entity p_212361_1_) {
        if (p_212361_1_ != null) {
            this.ownerUUID = p_212361_1_.getUUID();
            this.ownerNetworkId = p_212361_1_.getId();
        }

    }

    @Nullable
    public Entity getOwner() {
        IniField.LOGGER.debug("ownerNetworkId:"+this.ownerNetworkId+"---------------------------------------------");
        IniField.LOGGER.debug("ownerUUId:"+this.ownerUUID+"---------------------------------------------");
        if (this.ownerUUID != null && this.level instanceof ServerWorld) {
            return ((ServerWorld)this.level).getEntity(this.ownerUUID);
        } else {
            IniField.LOGGER.debug("else ownerNetworkId:"+this.ownerNetworkId+"---------------------------------------------");

            return this.ownerNetworkId != 0 ? this.level.getEntity(this.ownerNetworkId) : null;
        }
    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        if (this.ownerUUID != null) {
            p_213281_1_.putUUID("Owner", this.ownerUUID);
        }

        if (this.leftOwner) {
            p_213281_1_.putBoolean("LeftOwner", true);
        }

    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        if (p_70037_1_.hasUUID("Owner")) {
            this.ownerUUID = p_70037_1_.getUUID("Owner");
        }

        this.leftOwner = p_70037_1_.getBoolean("LeftOwner");
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(ParticleTypes.CRIT, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
        }

        super.aiStep();
    }

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        IniField.LOGGER.debug("on hit entity");
        Entity entity = p_213868_1_.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), (float)0);

        this.dealtDamage = true;
        this.isReturn=true;
//        this.spawnAtLocation(new ItemStack(ModItems.CB_BLOCK_ITEM.get(),1));
//        this.remove();
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {

//        Vector3d vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
//        this.setDeltaMovement(vector3d);
//        Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
//        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.dealtDamage = true;
        this.isReturn=true;

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));

    }

    protected void onHit(RayTraceResult p_70227_1_) {
        RayTraceResult.Type raytraceresult$type = p_70227_1_.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onHitEntity((EntityRayTraceResult)p_70227_1_);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            this.onHitBlock((BlockRayTraceResult)p_70227_1_);
        }

    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        this.setNoGravity(true);
        noPhysics=true;
        isReturn=false;
        setDeltaMovement(Vector3d.ZERO);
        isCreative=true;
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    protected boolean canHitEntity(Entity p_230298_1_) {
        return true;
    }

    public void playerTouch(PlayerEntity p_70100_1_) {
        Entity entity = this.getOwner();
        if (isReturn) {
            if (!this.level.isClientSide && (this.inGround || this.noPhysics)) {

                if (entity==null||this.getOwner().getUUID() == p_70100_1_.getUUID()) {
                    p_70100_1_.addItem(new ItemStack(ModItems.CB_BLOCK_ITEM.get(),1));
                }else{
                    this.spawnAtLocation(new ItemStack(ModItems.CB_BLOCK_ITEM.get(),1));
                }
                this.remove();

            }
        }
    }

    public void tick() {

        Entity entity3 = this.getOwner();

        if ((this.dealtDamage)&& entity3 != null) {

            this.noPhysics=true;
            this.isReturn=true;

            if(entity3!=null){
                if(!isAcceptibleReturnOwner()){
                    if (!this.level.isClientSide) {
                        this.spawnAtLocation(new ItemStack(ModItems.CB_BLOCK_ITEM.get(),1));
                        remove();
                    }

                }
                deltaVector3d = new Vector3d(entity3.getX() - this.getX(), entity3.getEyeY() - this.getY(), entity3.getZ() - this.getZ());

            }


            this.setPos(this.getX(), this.getY() + deltaVector3d.y * 0.015D * 1, this.getZ());
            IniField.LOGGER.debug("set pos raw"+this.getX()+" "+this.getY() + deltaVector3d.y * 0.015D * 1+" "+this.getZ());

            if (this.level.isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05D*3;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(deltaVector3d.normalize().scale(d0)));
            IniField.LOGGER.debug(""+this.getDeltaMovement().x+" "+this.getDeltaMovement().y+" "+this.getDeltaMovement().z);

        }


        ++this.time;

        if (this.level instanceof ServerWorld&&this.getDeltaMovement()==Vector3d.ZERO&&this.isNoGravity()) {
            BlockPos blockpos = this.blockPosition();
            if (this.level.getBlockState(blockpos).isAir()) {
                this.level.setBlockAndUpdate(blockpos, ModBlocks.LIGHTING_BLOCK.get().defaultBlockState());
            }
        }

        //abstractArrow

        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockRayTraceResult)raytraceresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                TileEntity tileentity = this.level.getBlockEntity(blockpos);
                if (tileentity instanceof EndGatewayTileEntity && EndGatewayTileEntity.canEntityTeleport(this)) {
                    ((EndGatewayTileEntity)tileentity).teleportEntity(this);
                }

                flag = true;
            }
        }

        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !isCreative && !isReturn && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }

        this.checkInsideBlocks();
        Vector3d vector3d = this.getDeltaMovement();
        double d2 = this.getX() + vector3d.x;
        double d0 = this.getY() + vector3d.y;
        double d1 = this.getZ() + vector3d.z;
        this.updateRotation();
        float f;
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                float f1 = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, d2 - vector3d.x * 0.25D, d0 - vector3d.y * 0.25D, d1 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
            }

            f = 0.8F;
        } else {
            f = 0.99F;
        }

        this.setDeltaMovement(vector3d.scale((double)f));
        if (!this.isNoGravity()) {
            Vector3d vector3d1 = this.getDeltaMovement();
            this.setDeltaMovement(vector3d1.x, vector3d1.y - (double)this.getGravity(), vector3d1.z);
        }

        this.setPos(d2, d0, d1);
        IniField.LOGGER.debug("set pos"+d2+" "+d0+" "+d1);


    }

    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d p_213866_1_, Vector3d p_213866_2_) {
        return ProjectileHelper.getEntityHitResult(this.level, this, p_213866_1_, p_213866_2_, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    protected float getWaterInertia() {
        return 0.6F;
    }


    protected float getGravity() {
        return 0.03F;
    }

    protected void updateRotation() {
        Vector3d vector3d = this.getDeltaMovement();
        float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
        this.xRot = lerpRotation(this.xRotO, (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI)));
        this.yRot = lerpRotation(this.yRotO, (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI)));
    }

    protected static float lerpRotation(float p_234614_0_, float p_234614_1_) {
        while(p_234614_1_ - p_234614_0_ < -180.0F) {
            p_234614_0_ -= 360.0F;
        }

        while(p_234614_1_ - p_234614_0_ >= 180.0F) {
            p_234614_0_ += 360.0F;
        }

        return MathHelper.lerp(0.2F, p_234614_0_, p_234614_1_);
    }

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (!this.removed && !this.level.isClientSide) {
            this.spawnAtLocation(new ItemStack(ModItems.CB_BLOCK_ITEM.get(),1));
            this.level.setBlock(this.blockPosition(),Blocks.AIR.defaultBlockState(),3);
            this.remove();
        }

        return true;
    }

    public void shoot(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
        Vector3d vector3d = (new Vector3d(p_70186_1_, p_70186_3_, p_70186_5_)).normalize().add(this.random.nextGaussian() * (double)0.0075F * (double)p_70186_8_, this.random.nextGaussian() * (double)0.0075F * (double)p_70186_8_, this.random.nextGaussian() * (double)0.0075F * (double)p_70186_8_).scale((double)p_70186_7_);
        this.setDeltaMovement(vector3d);
        float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
        this.yRot = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
        this.xRot = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
        this.yRotO = this.yRot;
        this.xRotO = this.xRot;
    }

    public void shootFromRotation(Entity p_234612_1_, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
        float f = -MathHelper.sin(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
        float f1 = -MathHelper.sin((p_234612_2_ + p_234612_4_) * ((float)Math.PI / 180F));
        float f2 = MathHelper.cos(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, p_234612_5_, p_234612_6_);
        Vector3d vector3d = p_234612_1_.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vector3d.x, p_234612_1_.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
    }
}
