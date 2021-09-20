package com.turtywurty.newmobs.common.entities.goals;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

import com.turtywurty.newmobs.common.entities.MoobloomEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BeeToMoobloomGoal extends Goal {

	private boolean running;
	private BeeEntity bee;
	private Predicate<MoobloomEntity> flowerPredicate;
	private int pollinationTicks, ticks, lastPollinationTick;
	private Vec3d nextTarget;

	public BeeToMoobloomGoal(BeeEntity bee) {
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		this.bee = bee;
		this.flowerPredicate = moobloom -> moobloom instanceof MoobloomEntity;
	}

	public boolean shouldExecute() {
		if (this.bee.remainingCooldownBeforeLocatingNewFlower > 0) {
			return false;
		} else if (bee.hasNectar()) {
			return false;
		} else if (bee.world.isRaining()) {
			return false;
		} else if (bee.rand.nextFloat() < 0.7F) {
			return false;
		} else {
			Optional<MoobloomEntity> optional = this.getFlower();
			if (optional.isPresent()) {
				bee.savedFlowerPos = optional.get().getPosition();
				bee.navigator.tryMoveToXYZ((double) bee.savedFlowerPos.getX() + 0.5D,
						(double) bee.savedFlowerPos.getY() + 1D, (double) bee.savedFlowerPos.getZ() + 0.5D,
						(double) 1.2F);
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean canBeeContinue() {
		if (!this.running) {
			return false;
		} else if (!bee.hasFlower()) {
			return false;
		} else if (bee.world.isRaining()) {
			return false;
		} else if (this.completedPollination()) {
			return bee.rand.nextFloat() < 0.2F;
		} else if (bee.ticksExisted % 20 == 0
				&& !((bee.world.getEntitiesWithinAABB(MoobloomEntity.class, new AxisAlignedBB(bee.savedFlowerPos))
						.get(0)) instanceof MoobloomEntity)) {
			bee.savedFlowerPos = null;
			return false;
		} else {
			return true;
		}
	}

	private boolean completedPollination() {
		return this.pollinationTicks > 10;
	}

	private boolean isRunning() {
		return this.running;
	}

	private void cancel() {
		this.running = false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.pollinationTicks = 0;
		this.ticks = 0;
		this.lastPollinationTick = 0;
		this.running = true;
		bee.resetTicksWithoutNectar();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void resetTask() {
		if (this.completedPollination()) {
			bee.setHasNectar(true);
		}

		this.running = false;
		bee.navigator.clearPath();
		bee.remainingCooldownBeforeLocatingNewFlower = 200;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		++this.ticks;
		if (this.ticks > 600) {
			bee.savedFlowerPos = null;
		} else {
			Vec3d vec3d = (new Vec3d(bee.savedFlowerPos)).add(0.5D, (double) 0.6F, 0.5D);
			if (vec3d.distanceTo(bee.getPositionVec()) > 1.0D) {
				this.nextTarget = vec3d;
				this.moveToNextTarget();
			} else {
				if (this.nextTarget == null) {
					this.nextTarget = vec3d;
				}

				boolean flag = bee.getPositionVec().distanceTo(this.nextTarget) <= 0.1D;
				boolean flag1 = true;
				if (!flag && this.ticks > 600) {
					bee.savedFlowerPos = null;
				} else {
					if (flag) {
						boolean flag2 = bee.rand.nextInt(100) == 0;
						if (flag2) {
							this.nextTarget = new Vec3d(vec3d.getX() + (double) this.getRandomOffset(), vec3d.getY(),
									vec3d.getZ() + (double) this.getRandomOffset());
							bee.navigator.clearPath();
						} else {
							flag1 = false;
						}

						bee.getLookController().setLookPosition(vec3d.getX(), vec3d.getY(), vec3d.getZ());
					}

					if (flag1) {
						this.moveToNextTarget();
					}

					++this.pollinationTicks;
					if (bee.rand.nextFloat() < 0.05F && this.pollinationTicks > this.lastPollinationTick + 60) {
						this.lastPollinationTick = this.pollinationTicks;
						bee.playSound(SoundEvents.ENTITY_BEE_POLLINATE, 1.0F, 1.0F);
					}

				}
			}
		}
	}

	private void moveToNextTarget() {
		bee.getMoveHelper().setMoveTo(this.nextTarget.getX(), this.nextTarget.getY(), this.nextTarget.getZ(),
				(double) 0.35F);
	}

	private float getRandomOffset() {
		return (bee.rand.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
	}

	private Optional<MoobloomEntity> getFlower() {
		return this.findMoobloom(this.flowerPredicate, 5.0D);
	}

	private Optional<MoobloomEntity> findMoobloom(Predicate<MoobloomEntity> predicate, double distance) {
		BlockPos blockpos = new BlockPos(bee);
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (int i = 0; (double) i <= distance; i = i > 0 ? -i : 1 - i) {
			for (int j = 0; (double) j < distance; ++j) {
				for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
					for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
						blockpos$mutable.setPos(blockpos).move(k, i - 1, l);
						if (blockpos.withinDistance(blockpos$mutable, distance) && !bee.world
								.getEntitiesWithinAABB(MoobloomEntity.class, new AxisAlignedBB(blockpos$mutable))
								.isEmpty()) {
							if (predicate.test(bee.world
									.getEntitiesWithinAABB(MoobloomEntity.class, new AxisAlignedBB(blockpos$mutable))
									.get(0))) {
								return Optional.of(bee.world.getEntitiesWithinAABB(MoobloomEntity.class,
										new AxisAlignedBB(blockpos$mutable)).get(0));
							}
						}
					}
				}
			}
		}

		return Optional.empty();
	}
}
