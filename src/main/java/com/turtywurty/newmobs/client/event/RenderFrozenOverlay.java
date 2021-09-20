package com.turtywurty.newmobs.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.core.capabilities.CapabilityHandler.FrozenCapabilityProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = NewMobs.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class RenderFrozenOverlay {

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void renderOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType().equals(ElementType.ALL)) {
			PlayerEntity player = Minecraft.getInstance().player;
			player.getCapability(FrozenCapabilityProvider.FREEZE_TIME).ifPresent(c -> {
				if (c.getFreezeTimeLeft() > 0) {
					c.setFreezeTimeLeft(c.getFreezeTimeLeft() - 1);
					Minecraft.getInstance().getTextureManager()
							.bindTexture(new ResourceLocation("minecraft:textures/block/frosted_ice_2.png"));
					RenderSystem.enableBlend();
					RenderSystem.defaultBlendFunc();
					RenderSystem.disableAlphaTest();
					draw(0, 0, 0, 0, event.getWindow().getWidth(), event.getWindow().getHeight(), 255, 255, 255, 255);
					RenderSystem.disableBlend();
					RenderSystem.enableAlphaTest();
				}
			});
		}
	}

	@SuppressWarnings("deprecation")
	public static void draw(int posX, int posY, int texU, int texV, int width, int height, int red, int green, int blue,
			int alpha) {
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos((double) posX, (double) (posY + height), 0.0D)
				.tex(((float) texU * 0.00390625F), ((float) (texV + height) * 0.00390625F))
				.color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos((double) (posX + width), (double) (posY + height), 0.0D)
				.tex(((float) (texU + width) * 0.00390625F), ((float) (texV + height) * 0.00390625F))
				.color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos((double) (posX + width), (double) posY, 0.0D)
				.tex(((float) (texU + width) * 0.00390625F), ((float) texV * 0.00390625F))
				.color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos((double) posX, (double) posY, 0.0D)
				.tex(((float) texU * 0.00390625F), ((float) texV * 0.00390625F)).color(red, green, blue, alpha)
				.endVertex();
		Tessellator.getInstance().draw();
	}
}
