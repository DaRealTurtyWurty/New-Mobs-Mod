package com.turtywurty.newmobs.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.turtywurty.newmobs.common.entities.IceCloudEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class IceCloudModel extends EntityModel<IceCloudEntity> {

	private final ModelRenderer cloud;

	public IceCloudModel() {
		super(RenderType::getEntityTranslucent);

		this.textureWidth = 128;
		this.textureHeight = 128;

		this.cloud = new ModelRenderer(this);
		this.cloud.setRotationPoint(-1.0F, 10.0F, 1.0F);
		this.cloud.setTextureOffset(0, 21).addBox(-8.0F, -3.0F, -14.0F, 10.0F, 6.0F, 14.0F, 0.0F, false);
		this.cloud.setTextureOffset(36, 36).addBox(0.0F, -2.0F, 0.0F, 10.0F, 5.0F, 12.0F, 0.0F, false);
		this.cloud.setTextureOffset(38, 0).addBox(2.0F, -3.0F, -2.0F, 10.0F, 6.0F, 2.0F, 0.0F, false);
		this.cloud.setTextureOffset(0, 0).addBox(2.0F, -3.5F, -16.0F, 12.0F, 7.0F, 14.0F, 0.0F, false);
		this.cloud.setTextureOffset(34, 21).addBox(-12.0F, -1.0F, 0.0F, 12.0F, 5.0F, 8.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(IceCloudEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		this.cloud.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
