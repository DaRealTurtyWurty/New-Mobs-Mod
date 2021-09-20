package com.turtywurty.newmobs.client.entity.render;

import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.client.entity.render.layers.MoobloomFlowerLayer;
import com.turtywurty.newmobs.common.entities.MoobloomEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;

public class MoobloomRenderer extends MobRenderer<MoobloomEntity, CowModel<MoobloomEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(NewMobs.MOD_ID,
			"textures/entity/moobloom.png");

	public MoobloomRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new CowModel<>(), 0.7F);
		this.addLayer(new MoobloomFlowerLayer<MoobloomEntity>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(MoobloomEntity entity) {
		return TEXTURE;
	}
}