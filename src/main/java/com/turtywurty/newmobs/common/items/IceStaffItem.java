package com.turtywurty.newmobs.common.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.turtywurty.newmobs.common.entities.IceCloudEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceStaffItem extends Item {

	public IceStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!playerIn.getCooldownTracker().hasCooldown(stack.getItem())) {
			RayTraceResult result = Item.rayTrace(worldIn, playerIn, FluidMode.NONE);
			BlockPos pos = new BlockPos(result.getHitVec());
			IceCloudEntity iceCloud = new IceCloudEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
			worldIn.addEntity(iceCloud);
			playerIn.getCooldownTracker().setCooldown(stack.getItem(), 400);
			return ActionResult.resultSuccess(stack);
		}
		return ActionResult.resultFail(stack);
	}

	public static void useStaff(World worldIn, ItemStack stack, @Nullable PlayerEntity playerIn,
			@Nonnull LivingEntity living) {
		if (playerIn != null) {
			if (!playerIn.getCooldownTracker().hasCooldown(stack.getItem())) {
				RayTraceResult result = Item.rayTrace(worldIn, playerIn, FluidMode.NONE);
				BlockPos pos = new BlockPos(result.getHitVec());
				IceCloudEntity iceCloud = new IceCloudEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
				worldIn.addEntity(iceCloud);
				if(!playerIn.isCreative()) {
					playerIn.getCooldownTracker().setCooldown(stack.getItem(), 400);
				}
			}
		} else {
			RayTraceResult result = rayTraceNoPlayer(worldIn, FluidMode.NONE, living);
			BlockPos pos = new BlockPos(result.getHitVec());
			IceCloudEntity iceCloud = new IceCloudEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
			worldIn.addEntity(iceCloud);
		}
	}

	protected static RayTraceResult rayTraceNoPlayer(World worldIn, RayTraceContext.FluidMode fluidMode,
			LivingEntity living) {
		float f = living.rotationPitch;
		float f1 = living.rotationYaw;
		Vec3d vec3d = living.getEyePosition(1.0F);
		float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
		float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d0 = 5.0D;
		Vec3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
		return worldIn.rayTraceBlocks(
				new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, living));
	}
}
