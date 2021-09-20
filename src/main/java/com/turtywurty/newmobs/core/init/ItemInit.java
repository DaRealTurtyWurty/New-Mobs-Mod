package com.turtywurty.newmobs.core.init;

import com.turtywurty.newmobs.NewMobs;
import com.turtywurty.newmobs.common.items.IceStaffItem;
import com.turtywurty.newmobs.common.items.ModSpawnEggItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NewMobs.MOD_ID);

	public static final RegistryObject<IceStaffItem> ICE_STAFF = ITEMS.register("ice_staff",
			() -> new IceStaffItem(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).maxDamage(400)));

	public static final RegistryObject<ModSpawnEggItem> ICEOLOGER_SPAWN_EGG = ITEMS.register("iceologer_spawn_egg",
			() -> new ModSpawnEggItem(EntityTypeInit.ICEOLOGER, 0x396AFF, 0x39A8E5,
					new Item.Properties().group(ItemGroup.MISC).maxStackSize(16)));

	public static final RegistryObject<ModSpawnEggItem> MOOBLOOM_SPAWN_EGG = ITEMS.register("moobloom_spawn_egg",
			() -> new ModSpawnEggItem(EntityTypeInit.MOOBLOOM, 0xDBDC00, 0xFFFFFF,
					new Item.Properties().group(ItemGroup.MISC).maxStackSize(16)));

	public static final RegistryObject<ModSpawnEggItem> GLOWSQUID_SPAWN_EGG = ITEMS.register("glowsquid_spawn_egg",
			() -> new ModSpawnEggItem(EntityTypeInit.GLOWSQUID, 0x2425FF, 0x009EC8,
					new Item.Properties().group(ItemGroup.MISC).maxStackSize(16)));
}
