package com.turtywurty.newmobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.turtywurty.newmobs.core.capabilities.CapabilityHandler;
import com.turtywurty.newmobs.core.init.EntityTypeInit;
import com.turtywurty.newmobs.core.init.ItemInit;
import com.turtywurty.newmobs.core.init.SoundInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("newmobs")
public class NewMobs {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "newmobs";
	public static NewMobs instance;

	public NewMobs() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);

		SoundInit.SOUNDS.register(modEventBus);
		ItemInit.ITEMS.register(modEventBus);
		EntityTypeInit.TYPES.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);

		instance = this;
	}

	private void setup(final FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(CapabilityHandler.IFrozenCapability.class,
				new CapabilityHandler.FrozenCapabilityStorage(), CapabilityHandler.FrozenCapability::new);
	}
}
