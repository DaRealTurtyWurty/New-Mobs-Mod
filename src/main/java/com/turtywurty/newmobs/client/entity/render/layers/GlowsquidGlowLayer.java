package com.turtywurty.newmobs.client.entity.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.common.entities.GlowsquidEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class GlowsquidGlowLayer extends LayerRenderer<GlowsquidEntity, SquidModel<GlowsquidEntity>> {

	private ResourceLocation[] textures = new ResourceLocation[22];

	public GlowsquidGlowLayer(IEntityRenderer<GlowsquidEntity, SquidModel<GlowsquidEntity>> entityRendererIn) {
		super(entityRendererIn);
		for (int index = 0; index < 22; index++) {
			this.textures[index] = new ResourceLocation(NewMobs.MOD_ID,
					"textures/entity/glow_squid/glow_squid_" + index + ".png");
		}
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
			GlowsquidEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
		this.getEntityModel().render(matrixStackIn,
				bufferIn.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(entitylivingbaseIn))),
				15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(GlowsquidEntity entityIn) {
		int textureIndex = (int) (((Minecraft.getInstance().getRenderPartialTicks()
				+ entityIn.getEntityWorld().getGameTime()) * 2) % this.textures.length);
		return this.textures[textureIndex];
	}
}
