package com.turtywurty.newmobs.common.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MoobloomEntity extends MooshroomEntity {

	public MoobloomEntity(EntityType<? extends MoobloomEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public boolean processInteract(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (itemstack.getItem() == Items.SHEARS && !this.isChild()) {
			this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(),
					0.0D, 0.0D, 0.0D);
			if (!this.world.isRemote) {
				this.remove();
				CowEntity cowentity = EntityType.COW.create(this.world);
				cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw,
						this.rotationPitch);
				cowentity.setHealth(this.getHealth());
				cowentity.renderYawOffset = this.renderYawOffset;
				if (this.hasCustomName()) {
					cowentity.setCustomName(this.getCustomName());
					cowentity.setCustomNameVisible(this.isCustomNameVisible());
				}

				if (this.isNoDespawnRequired()) {
					cowentity.enablePersistence();
				}

				cowentity.setInvulnerable(this.isInvulnerable());
				this.world.addEntity(cowentity);

				for (int k = 0; k < 5; ++k) {
					this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosYHeight(1.0D),
							this.getPosZ(), new ItemStack(Blocks.DANDELION)));
				}

				itemstack.damageItem(1, player, (p_213442_1_) -> {
					p_213442_1_.sendBreakAnimation(hand);
				});
				this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
			}

			return true;
		}
		return false;
	}
}
