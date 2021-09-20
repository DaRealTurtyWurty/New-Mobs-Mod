package com.turtywurty.newmobs.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.client.entity.model.IceCloudModel;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class IceCloudRenderer<T extends Entity> extends EntityRenderer<T> {

	private IceCloudModel model;

	private static final ResourceLocation TEXTURE = new ResourceLocation(NewMobs.MOD_ID,
			"textures/entity/ice_cloud.png");

	public IceCloudRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		this.model = new IceCloudModel();
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return TEXTURE;
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		this.model.render(matrixStackIn,
				bufferIn.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(entityIn))), packedLightIn,
				OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
