package com.turtywurty.newmobs.common.entities;

import java.util.List;

import com.turtywurty.newmobs.core.capabilities.CapabilityHandler;
import com.turtywurty.newmobs.core.capabilities.CapabilityHandler.IFrozenCapability;
import com.turtywurty.newmobs.core.init.EntityTypeInit;

import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;

public class IceCloudEntity extends Entity {
	protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(IceCloudEntity.class,
			DataSerializers.BLOCK_POS);

	public IceCloudEntity(EntityType<? extends Entity> type, World world) {
		super(type, world);
	}

	public IceCloudEntity(World worldIn, double x, double y, double z) {
		this(EntityTypeInit.ICE_CLOUD.get(), worldIn);
		this.preventEntitySpawning = true;
		this.setPosition(x, y + (double) ((1.0F - this.getHeight()) / 2.0F), z);
		this.setMotion(Vec3d.ZERO);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.setOrigin(new BlockPos(this));
	}

	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	public void setOrigin(BlockPos pos) {
		this.dataManager.set(ORIGIN, pos);
	}

	@OnlyIn(Dist.CLIENT)
	public BlockPos getOrigin() {
		return this.dataManager.get(ORIGIN);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void registerData() {
		this.dataManager.register(ORIGIN, BlockPos.ZERO);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canBeCollidedWith() {
		return !this.removed;
	}

	@Override
	public void tick() {
		this.setPosition(this.getPosX(), this.getPosY() - 0.5D, this.getPosZ());

		this.world.getProfiler().startSection("push");
		this.collideWithNearbyEntities();
		this.world.getProfiler().endSection();

		if (this.onGround || this.isEntityInsideOpaqueBlock()) {
			this.remove();
		}
	}

	protected void collideWithNearbyEntities() {
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(),
				EntityPredicates.pushableBy(this));
		if (!list.isEmpty()) {
			int i = this.world.getGameRules().getInt(GameRules.MAX_ENTITY_CRAMMING);
			if (i > 0 && list.size() > i - 1 && this.rand.nextInt(4) == 0) {
				int j = 0;

				for (int k = 0; k < list.size(); ++k) {
					if (!list.get(k).isPassenger()) {
						++j;
					}
				}

				if (j > i - 1) {
					this.attackEntityFrom(DamageSource.CRAMMING, 6.0F);
				}
			}

			for (int l = 0; l < list.size(); ++l) {
				Entity entity = list.get(l);
				this.collideWithEntity(entity);
			}
		}
	}

	protected void collideWithEntity(Entity entityIn) {
		entityIn.applyEntityCollision(this);
		if (entityIn.getClass() != this.getClass() && entityIn.getClass() != IceCloudEntity.class && !this.onGround
				&& this.prevPosY > entityIn.prevPosY) {
			entityIn.attackEntityFrom(DamageSource.FALLING_BLOCK, 3.0f);
			if (entityIn instanceof LivingEntity) {
				((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, 8, false, false));
				LazyOptional<IFrozenCapability> cap = entityIn
						.getCapability(CapabilityHandler.FrozenCapabilityProvider.FREEZE_TIME);
				cap.ifPresent((icap) -> {
					icap.setFreezeTimeLeft(icap.getFreezeTimeLeft() + 350);
				});
			}
		}
	}

	@Override
	public void remove() {
		this.world.setBlockState(this.getPosition(), Blocks.ICE.getDefaultState(), 3);
		for (int i = 0; i < 10; i++) {
			this.world.addParticle(
					new BlockParticleData(ParticleTypes.BLOCK,
							Blocks.FROSTED_ICE.getDefaultState().with(FrostedIceBlock.AGE, 2)),
					this.getPosX(), this.getPosY() + 1.5D, this.getPosZ(), rand.nextDouble(), rand.nextDouble(),
					rand.nextDouble());
		}
		super.remove();
	}

	@OnlyIn(Dist.CLIENT)
	public World getWorldObj() {
		return this.world;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public boolean ignoreItemEntityData() {
		return true;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}
}