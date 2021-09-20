package com.turtywurty.newmobs.core.init;

import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.common.entities.GlowsquidEntity;
import com.turtywurty.newmobs.common.entities.IceCloudEntity;
import com.turtywurty.newmobs.common.entities.IceologerEntity;
import com.turtywurty.newmobs.common.entities.MoobloomEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {

	public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			NewMobs.MOD_ID);

	public static final RegistryObject<EntityType<GlowsquidEntity>> GLOWSQUID = TYPES.register("glowsquid",
			() -> EntityType.Builder.create(GlowsquidEntity::new, EntityClassification.WATER_CREATURE).size(0.8F, 0.8F)
					.build(new ResourceLocation(NewMobs.MOD_ID, "glowsquid").toString()));

	public static final RegistryObject<EntityType<MoobloomEntity>> MOOBLOOM = TYPES.register("moobloom",
			() -> EntityType.Builder.create(MoobloomEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F)
					.build(new ResourceLocation(NewMobs.MOD_ID, "moobloom").toString()));

	public static final RegistryObject<EntityType<IceologerEntity>> ICEOLOGER = TYPES.register("iceologer",
			() -> EntityType.Builder.create(IceologerEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F)
					.build(new ResourceLocation(NewMobs.MOD_ID, "iceologer").toString()));

	public static final RegistryObject<EntityType<?>> ICE_CLOUD = TYPES.register("ice_cloud",
			() -> EntityType.Builder.create(IceCloudEntity::new, EntityClassification.MISC).size(1.0F, 0.6f)
					.build(new ResourceLocation(NewMobs.MOD_ID, "ice_cloud").toString()));
}
