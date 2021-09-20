package com.turtywurty.newmobs.core.capabilities;

import com.turtywurty.newmobs.NewMobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = NewMobs.MOD_ID, bus = Bus.FORGE)
public class CapabilityHandler {

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof LivingEntity) {
			event.addCapability(new ResourceLocation(NewMobs.MOD_ID, "freeze_time_left"), new FrozenCapabilityProvider());
		}
	}

	public static class FrozenCapabilityProvider implements ICapabilityProvider {
		@CapabilityInject(IFrozenCapability.class)
		public static final Capability<IFrozenCapability> FREEZE_TIME = null;

		private IFrozenCapability instance = FREEZE_TIME.getDefaultInstance();

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == FREEZE_TIME ? LazyOptional.of(() -> instance).cast() : LazyOptional.empty();
		}
	}

	public interface IFrozenCapability {
		public int getFreezeTimeLeft();

		public void setFreezeTimeLeft(int time);
	}

	public static class FrozenCapability implements IFrozenCapability {
		private int freezeTimeLeft;

		@Override
		public int getFreezeTimeLeft() {
			return this.freezeTimeLeft;
		}

		@Override
		public void setFreezeTimeLeft(int time) {
			this.freezeTimeLeft = time;
		}
	}

	public static class FrozenCapabilityStorage implements Capability.IStorage<IFrozenCapability> {
		@Override
		public void readNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, Direction side,
				INBT nbt) {
			capability.getDefaultInstance()
					.setFreezeTimeLeft(nbt instanceof CompoundNBT ? ((CompoundNBT) nbt).getInt("FreezeTimeLeft") : 0);
		}

		@Override
		public INBT writeNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, Direction side) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putInt("FreezeTimeLeft", capability.getDefaultInstance().getFreezeTimeLeft());
			return nbt;
		}
	}
}
