package com.turtywurty.newmobs.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.client.entity.model.IceologerModel;
import com.turtywurty.newmobs.client.entity.render.layers.StaffItemRenderLayer;
import com.turtywurty.newmobs.common.entities.IceologerEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class IceologerRenderer extends MobRenderer<IceologerEntity, IceologerModel<IceologerEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(NewMobs.MOD_ID,
			"textures/entity/iceologer.png");

	public IceologerRenderer(EntityRendererManager manager) {
		super(manager, new IceologerModel(), 0.5F);
		this.addLayer(new StaffItemRenderLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(IceologerEntity entity) {
		return TEXTURE;
	}

	@Override
	protected void preRenderCallback(IceologerEntity entity, MatrixStack stack, float partialTicks) {
		stack.scale(0.9375F, 0.9375F, 0.9375F);
	}
}