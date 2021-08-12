package com.imjustlazy.lootbags.items;

import java.lang.reflect.Field;

import com.imjustlazy.lootbags.Constants;
import com.imjustlazy.lootbags.Lootbags;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Lootbags.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class LootbagItemRegistry {
	
	public static ItemLootbag COMMON_LOOTBAG = new ItemLootbag(Constants.COMMON_LOOTBAG);
	public static ItemLootbag UNCOMMON_LOOTBAG = new ItemLootbag(Constants.UNCOMMON_LOOTBAG);
	public static ItemLootbag RARE_LOOTBAG = new ItemLootbag(Constants.RARE_LOOTBAG);
	public static ItemLootbag EPIC_LOOTBAG = new ItemLootbag(Constants.EPIC_LOOTBAG);
	public static ItemLootbag LEGENDARY_LOOTBAG = new ItemLootbag(Constants.LEGENDARY_LOOTBAG);
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		try {
			for(Field f : LootbagItemRegistry.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if(obj instanceof Item) {
					event.getRegistry().register((Item) obj);
				}
			}
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
