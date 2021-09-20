package com.turtywurty.newmobs.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.client.entity.render.layers.GlowsquidGlowLayer;
import com.turtywurty.newmobs.common.entities.GlowsquidEntity;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowsquidRenderer extends MobRenderer<GlowsquidEntity, SquidModel<GlowsquidEntity>> {
	private static final ResourceLocation SQUID_TEXTURES = new ResourceLocation(NewMobs.MOD_ID,
			"textures/entity/glow_squid.png");

	public GlowsquidRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new SquidModel<>(), 0.7F);
		this.addLayer(new GlowsquidGlowLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(GlowsquidEntity entity) {
		return SQUID_TEXTURES;
	}

	@Override
	protected void applyRotations(GlowsquidEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float pitchLerp = MathHelper.lerp(partialTicks, entityLiving.prevSquidPitch, entityLiving.squidPitch);
		float yawLerp = MathHelper.lerp(partialTicks, entityLiving.prevSquidYaw, entityLiving.squidYaw);
		matrixStackIn.translate(0.0D, 0.5D, 0.0D);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(pitchLerp));
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(yawLerp));
		matrixStackIn.translate(0.0D, (double) -1.2F, 0.0D);
	}

	@Override
	protected float handleRotationFloat(GlowsquidEntity livingBase, float partialTicks) {
		return MathHelper.lerp(partialTicks, livingBase.lastTentacleAngle, livingBase.tentacleAngle);
	}
}