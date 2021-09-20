package com.turtywurty.newmobs.client.event;

import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.client.entity.render.GlowsquidRenderer;
import com.turtywurty.newmobs.client.entity.render.IceCloudRenderer;
import com.turtywurty.newmobs.client.entity.render.IceologerRenderer;
import com.turtywurty.newmobs.client.entity.render.MoobloomRenderer;
import com.turtywurty.newmobs.client.entity.render.layers.IceLayer;
import com.turtywurty.newmobs.core.init.EntityTypeInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NewMobs.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEventBus {

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICEOLOGER.get(), IceologerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MOOBLOOM.get(), MoobloomRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.GLOWSQUID.get(), GlowsquidRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_CLOUD.get(), IceCloudRenderer::new);
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void applyIceLayer(FMLClientSetupEvent event) {
		Minecraft.getInstance().getRenderManager().renderers.forEach((type, renderer) -> {
			if (renderer instanceof LivingRenderer) {
				LivingRenderer livingRenderer = (LivingRenderer) renderer;
				livingRenderer.addLayer(new IceLayer((IEntityRenderer) renderer));
			}
		});
	}
}
