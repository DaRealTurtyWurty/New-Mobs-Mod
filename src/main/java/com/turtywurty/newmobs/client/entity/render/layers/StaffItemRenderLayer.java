package com.turtywurty.newmobs.client.entity.render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.newmobs.client.entity.model.IceologerModel;
import com.turtywurty.newmobs.common.entities.IceologerEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StaffItemRenderLayer<T extends IceologerEntity, M extends IceologerModel<T> & IHasArm>
		extends LayerRenderer<T, M> {

	public StaffItemRenderLayer(IEntityRenderer<T, M> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T livingEntity,
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.getEntityModel().translateHand(
				livingEntity.getActiveHand() == Hand.MAIN_HAND ? HandSide.LEFT : HandSide.RIGHT, matrixStackIn);
		Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(livingEntity,
				livingEntity.getHeldItem(livingEntity.getActiveHand()),
				livingEntity.getActiveHand() == Hand.MAIN_HAND ? TransformType.THIRD_PERSON_LEFT_HAND
						: TransformType.THIRD_PERSON_RIGHT_HAND,
				livingEntity.getActiveHand() == Hand.MAIN_HAND, matrixStackIn, bufferIn, packedLightIn);
	}
}
