package com.imjustlazy.lootbags.config.json.tables;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.imjustlazy.lootbags.Constants;

public class LootTables {

	public static List<String> commonTableEntities = Lists.newArrayList(mobList());
	public static LootTableDataList commonTable = new LootTableDataList(new LootMetadata(Constants.COMMON_LOOTBAG, commonTableEntities, 0.2, 3));
	public static List<String> uncommonTableEntities = Lists.newArrayList(mobList());
	public static LootTableDataList uncommonTable = new LootTableDataList(new LootMetadata(Constants.UNCOMMON_LOOTBAG, uncommonTableEntities, 0.15, 3));
	public static List<String> rareTableEntities = Lists.newArrayList(mobList());
	public static LootTableDataList rareTable = new LootTableDataList(new LootMetadata(Constants.RARE_LOOTBAG, rareTableEntities, 0.1, 3));
	public static List<String> epicTableEntities = Lists.newArrayList(mobList());
	public static LootTableDataList epicTable = new LootTableDataList(new LootMetadata(Constants.EPIC_LOOTBAG, epicTableEntities, 0.05, 3));
	public static List<String> legendaryTableEntities = Lists.newArrayList(bossList());
	public static LootTableDataList legendaryTable = new LootTableDataList(new LootMetadata(Constants.LEGENDARY_LOOTBAG, legendaryTableEntities, 0.66, 1));

	static {
		// Construct premade tables.
		commonTable.addItemData(new LootItemData("minecraft:diamond", 1, 10, 1));
		commonTable.addItemData(new LootItemData("minecraft:emerald", 1, 10, 1));
		commonTable.addItemData(new LootItemData("minecraft:redstone", 1, 10, 1));
		commonTable.addItemData(new LootItemData("minecraft:lapis_lazuli", 1, 10, 1));
		commonTable.addItemData(new LootItemData("minecraft:torch", 1, 10, 1));
		commonTable.addItemData(new LootItemData("minecraft:cobblestone", 1, 10, 1));
		
		uncommonTable.addItemData(new LootItemData("minecraft:cobblestone", 1, 64, 999));
		uncommonTable.addItemData(new LootItemData("minecraft:wither_star", 1, 64, 1));
		
		rareTable.addItemData(new LootItemData("minecraft:feather", 1, 32, 1));
		
		epicTable.addItemData(new LootItemData("minecraft:feather", 1, 32, 1));
		
		legendaryTable.addItemData(new LootItemData("minecraft:nether_star", 1, 1, 1));
		legendaryTable.addItemData(new LootItemData("minecraft:beacon", 1, 1, 1));
		legendaryTable.addItemData(new LootItemData("minecraft:diamond_block", 1, 1, 1));
	}
	
	// Returns a default generated loot table or if one is not found, null.
	public static LootTableDataList getDefaultTable(String tableName) {
		switch(tableName) {
		case Constants.COMMON_LOOTBAG: return commonTable;
		case Constants.UNCOMMON_LOOTBAG: return uncommonTable;
		case Constants.RARE_LOOTBAG: return rareTable;
		case Constants.EPIC_LOOTBAG: return epicTable;
		case Constants.LEGENDARY_LOOTBAG: return legendaryTable;
		default: return null;
		}
	}
	
	public static ArrayList<String> mobList() {
		return Lists.newArrayList("minecraft:zombie", "minecraft:skeleton", "minecraft:spider", "minecraft:cave_spider", "minecraft:chicken");
	}
	
	public static ArrayList<String> bossList() {
		return Lists.newArrayList("minecraft:wither", "minecraft:ender_dragon", "minecraft:ravager");
	}
}
