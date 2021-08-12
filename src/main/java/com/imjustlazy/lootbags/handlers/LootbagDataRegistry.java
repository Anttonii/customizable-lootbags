package com.imjustlazy.lootbags.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imjustlazy.lootbags.Constants;
import com.imjustlazy.lootbags.Lootbags;
import com.imjustlazy.lootbags.common.NPCDropHandler;
import com.imjustlazy.lootbags.config.json.tables.LootMetadata;
import com.imjustlazy.lootbags.config.json.tables.LootTableDataList;

public class LootbagDataRegistry {
	
	// List of lootbag item names that will be registered by the json handler before the data streams are opened.
	private static List<String> preInit = new ArrayList<>();
	private static Map<String, LootTableDataList> registry = new HashMap<>();

	static {
		preInit.add(Constants.COMMON_LOOTBAG);
		preInit.add(Constants.UNCOMMON_LOOTBAG);
		preInit.add(Constants.RARE_LOOTBAG);
		preInit.add(Constants.EPIC_LOOTBAG);
		preInit.add(Constants.LEGENDARY_LOOTBAG);
	}
	
	public static void addLootbag(String lootbag, LootTableDataList data) {
		registry.put(lootbag, data);
		handleMetadata(data);
	}
	
	public static void addPreInit(String lootbag) {
		preInit.add(lootbag);
	}
	
	public static LootTableDataList get(String lootbag) {
		return registry.get(lootbag);
	}
	
	public static List<String> getPreInit() {
		return preInit;
	}
	
	public static Boolean contains(String key) {
		return registry.containsKey(key);
	}
	
	private static void handleMetadata(LootTableDataList data) {
		LootMetadata metadata = data.getMetadata();
		
		if(!metadata.getNpcIDs().isEmpty()) {
			for (String npcID : metadata.getNpcIDs())
				NPCDropHandler.registerDrop(npcID, Lootbags.MOD_ID + ":" + metadata.getItemID(), metadata.getLootOdds());
		}
	}
	
}
