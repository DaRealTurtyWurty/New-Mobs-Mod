package com.turtywurty.newmobs.common.entities.goals;

import java.util.EnumSet;

import com.turtywurty.newmobs.common.entities.IceCloudEntity;
import com.turtywurty.newmobs.common.items.IceStaffItem;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;

public class ThrowIceGoal<T extends MonsterEntity & IRangedAttackMob> extends Goal {

	private final T entity;
	private int attackCooldown = 0;
	private final float maxAttackDistance;

	public ThrowIceGoal(T mob, float maxAttackDistanceIn) {
		this.entity = mob;
		this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public void setAttackCooldown(int attackCooldownIn) {
		this.attackCooldown = attackCooldownIn;
	}

	@Override
	public boolean shouldExecute() {
		return this.entity.getAttackTarget() == null ? false : this.hasIceStaff();
	}

	public boolean hasIceStaff() {
		return this.entity.getHeldItemMainhand().getItem() instanceof IceStaffItem;
	}

	public boolean shouldContinueExecuting() {
		return (this.shouldExecute() || !this.entity.getNavigator().noPath()) && this.hasIceStaff();
	}

	public void startExecuting() {
		super.startExecuting();
		this.entity.setAggroed(true);
	}

	public void resetTask() {
		super.resetTask();
		this.entity.setAggroed(false);
		this.entity.resetActiveHand();
	}

	@Override
	public void tick() {
		super.tick();
		LivingEntity livingentity = this.entity.getAttackTarget();
		if (livingentity != null) {
			if (this.attackCooldown >= 200) {
				if (this.entity.getDistance(livingentity) <= this.maxAttackDistance) {
					IceCloudEntity iceCloud = new IceCloudEntity(this.entity.getEntityWorld(), livingentity.getPosX(),
							livingentity.getPosY() + 10.0D, livingentity.getPosZ());
					this.entity.world.addEntity(iceCloud);
				}
				this.attackCooldown = 0;
			}
			this.attackCooldown++;
		} else {
			this.attackCooldown = 0;
		}

	}
}
