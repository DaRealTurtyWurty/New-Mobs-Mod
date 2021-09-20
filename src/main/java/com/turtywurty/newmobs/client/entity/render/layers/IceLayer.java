package com.turtywurty.newmobs.client.entity.render.layers;

import java.util.BitSet;
import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.turtywurty.newmobs.core.capabilities.CapabilityHandler.FrozenCapabilityProvider;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

public class IceLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

	public IceLayer(IEntityRenderer<T, M> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T living,
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
			float headPitch) {
		living.getCapability(FrozenCapabilityProvider.FREEZE_TIME).ifPresent(cap -> {
			if(cap.getFreezeTimeLeft() > 0) {
				matrixStackIn.push();
				matrixStackIn.translate(-0.5D, 0.0D, -0.5D);
				renderFlat(living.getEntityWorld(), living.getPosition(), bufferIn.getBuffer(RenderType.getTranslucent()),
						EmptyModelData.INSTANCE, matrixStackIn,
						Blocks.FROSTED_ICE.getDefaultState().with(FrostedIceBlock.AGE, 2),
						Minecraft.getInstance().getBlockRendererDispatcher()
								.getModelForState(Blocks.FROSTED_ICE.getDefaultState().with(FrostedIceBlock.AGE, 2)),
						living.world.getRandom(), living.world.getSeed(), false, packedLightIn);
				matrixStackIn.pop();
			}
		});
	}

	public void renderFlat(World worldIn, BlockPos posIn, IVertexBuilder buffer, IModelData modelData,
			MatrixStack matrixStackIn, BlockState stateIn, IBakedModel modelIn, Random randomIn, long rand,
			boolean checkSides, int combinedOverlayIn) {
		BitSet bitset = new BitSet(3);

		for (Direction direction : Direction.values()) {
			randomIn.setSeed(rand);
			List<BakedQuad> list = modelIn.getQuads(stateIn, direction, randomIn, modelData);
			Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderQuadsFlat(worldIn,
					stateIn, posIn, 15728640, combinedOverlayIn, false, matrixStackIn, buffer, list, bitset);
		}
	}
}
