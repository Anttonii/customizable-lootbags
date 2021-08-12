package com.imjustlazy.lootbags.config;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class CommonConfig {

	private static final ForgeConfigSpec.Builder CONFIG = new ForgeConfigSpec.Builder();
	private static ForgeConfigSpec COMMON_CONFIG;
	
	public static BooleanValue ENABLE_MOB_DROPS;
	public static BooleanValue ENABLE_DUNGEON_LOOT;
	public static BooleanValue ENABLE_REWARD_SCREEN;
	
	static {
		initConfig();
	}
	
	private static void initConfig( ) {
		ENABLE_MOB_DROPS = CONFIG.comment("Whether or not lootbags are added as mob drops. Drops are configurable within the .json files.").define("enable_mob_drops", true);
		ENABLE_DUNGEON_LOOT = CONFIG.comment("Whether or not lootbags are added to dungeon chests.").define("enable_dungeon_loot", true);
		ENABLE_REWARD_SCREEN = CONFIG.comment("Whether or not a reward screen is shown at all when opening lootbags.").define("enable_rewards_screen", true);
		
		COMMON_CONFIG = CONFIG.build();
	}
	
	
	public static void setup(Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		configData.load();
		COMMON_CONFIG.setConfig(configData);
	}
}
