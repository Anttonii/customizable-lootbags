package com.imjustlazy.lootbags;

import java.nio.file.Path;

import net.minecraftforge.fml.loading.FMLPaths;

public class Constants {
	
	public static final String COMMON_LOOTBAG = "common_lootbag";
	public static final String UNCOMMON_LOOTBAG = "uncommon_lootbag";
	public static final String RARE_LOOTBAG = "rare_lootbag";
	public static final String EPIC_LOOTBAG = "epic_lootbag";
	public static final String LEGENDARY_LOOTBAG = "legendary_lootbag";

	private static final String CONFIG_FOLDER = "lootbags";
	private static final String LOOT_TABLES_FOLDER = "tables";
	public static final String CONFIG_COMMON_NAME = "lootbags-common";
	
	// Paths
	public static final Path CONFIG_FOLDER_PATH = FMLPaths.CONFIGDIR.get().resolve(Constants.CONFIG_FOLDER);
	public static final Path LOOT_TABLES_FOLDER_PATH = CONFIG_FOLDER_PATH.resolve(Constants.LOOT_TABLES_FOLDER);
	
	// Json properties
	public static final String PROPERTY_METADATA = "metadata";
	public static final String PROPERTY_ITEM_ID = "item_id";
	public static final String PROPERTY_DROP_ODDS = "drop_odds";
	public static final String PROPERTY_NPC_IDS = "npc_ids";
	public static final String PROPERTY_ROLLS = "rolls";
	public static final String PROPERTY_ITEMS = "items";
	public static final String PROPERTY_MAX = "max_count";
	public static final String PROPERTY_MIN = "min_count";
	public static final String PROPERTY_WEIGHT = "weight";
	
	public static final String REWARDS_SCREEN_TITLE = "lootbags.rewards.title";
	
}
