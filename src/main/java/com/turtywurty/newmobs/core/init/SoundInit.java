package com.turtywurty.newmobs.core.init;

import com.turtywurty.newmobs.NewMobs;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			NewMobs.MOD_ID);

	public static final RegistryObject<SoundEvent> ICEOLOGER_ATTACK = SOUNDS.register("iceologer.attack",
			() -> new SoundEvent(new ResourceLocation(NewMobs.MOD_ID, "iceologer.attack")));

	public static final RegistryObject<SoundEvent> ICEOLOGER_DEATH = SOUNDS.register("iceologer.death",
			() -> new SoundEvent(new ResourceLocation(NewMobs.MOD_ID, "iceologer.death")));

	public static final RegistryObject<SoundEvent> ICEOLOGER_HURT = SOUNDS.register("iceologer.hurt",
			() -> new SoundEvent(new ResourceLocation(NewMobs.MOD_ID, "iceologer.hurt")));

	public static final RegistryObject<SoundEvent> ICEOLOGER_IDLE = SOUNDS.register("iceologer.idle",
			() -> new SoundEvent(new ResourceLocation(NewMobs.MOD_ID, "iceologer.idle")));
}
