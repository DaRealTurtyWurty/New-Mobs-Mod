package com.turtywurty.newmobs.core.events;

import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.common.entities.goals.BeeToMoobloomGoal;

import net.minecraft.entity.passive.BeeEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = NewMobs.MOD_ID, bus = Bus.FORGE)
public class AddGoals {

	@SubscribeEvent
	public static void addBeeGoal(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof BeeEntity) {
			((BeeEntity) event.getEntity()).goalSelector.addGoal(1,
					new BeeToMoobloomGoal((BeeEntity) event.getEntity()));
		}
	}
}
