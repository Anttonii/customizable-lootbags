package com.imjustlazy.lootbags.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import com.imjustlazy.lootbags.config.CommonConfig;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

// Register lootbags as part of NPC drops.
public class NPCDropHandler {

	// A list is required so that we can register multiple drops to single entities but restring
	// the amount of lootbags dropped to 1.
	private static Map<String, List<Tuple<String, Double>>> dropRegistry = new HashMap<>();
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onMobDrops(LivingDropsEvent event) {
		LivingEntity entityKilled = (LivingEntity) event.getEntity();
		String entityID = entityKilled.getType().getRegistryName().toString();
		
		if(!CommonConfig.ENABLE_MOB_DROPS.get())
			return;
		
		// Making sure mob killers work, only stop items from dropping if the death is completely unrelated to the player.
		if(event.getSource() != DamageSource.IN_WALL)
			return;
		
		if(dropRegistry.containsKey(entityID)) {
			List<Tuple<String, Double>> lootTables = dropRegistry.get(entityID);
			
			Random random = new Random();
			int index = random.nextInt(lootTables.size());
			
			Tuple<String, Double> table = lootTables.get(index);
			String itemID = table.getA();
			double odds = table.getB();
			
			ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)));
			
			if(random.nextDouble() < odds)
				event.getDrops().add(new ItemEntity(entityKilled.level, entityKilled.getX(),
										entityKilled.getY(), entityKilled.getZ(), stack));
		}
	}
	
	public static void registerDrop(String entityName, String itemID, double odds) {
		if(!dropRegistry.containsKey(entityName))
			dropRegistry.put(entityName, Lists.newArrayList(new Tuple<>(itemID, odds)));
		else
			dropRegistry.get(entityName).add(new Tuple<>(itemID, odds));
	}
	
	public static Map<String, List<Tuple<String, Double>>> getDropRegistry() {
		return dropRegistry;
	}
}
