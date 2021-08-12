package com.imjustlazy.lootbags.config.json;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imjustlazy.lootbags.Constants;
import com.imjustlazy.lootbags.Lootbags;
import com.imjustlazy.lootbags.config.json.tables.LootTableDataList;
import com.imjustlazy.lootbags.config.json.tables.LootTableSerializer;
import com.imjustlazy.lootbags.config.json.tables.LootTables;
import com.imjustlazy.lootbags.handlers.LootbagDataRegistry;

// Loads json files to append loot to the Lootbag loot tables.
public class LootbagJsonHandler {
	
	public static void handleJsonFile(String fileName) {
		File jsonFile = new File(lootTablesPath().toString() + "/" + fileName + ".json");
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LootTableDataList.class, new LootTableSerializer());
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.disableHtmlEscaping();
		Gson gson = gsonBuilder.create();
		
		if(!jsonFile.exists()) {
			LootTableDataList data = LootTables.getDefaultTable(fileName);
			if(data != null)
				createJsonFile(fileName, data);
			else {
				Lootbags.LOGGER.warn("Failed to handle loot table for " + fileName + ", does not exists.");
				return;
			}
		}
		
		try(Reader reader = new FileReader(jsonFile.getAbsolutePath())) {
			LootTableDataList dataList = gson.fromJson(reader, LootTableDataList.class);
			LootbagDataRegistry.addLootbag(fileName, dataList);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void createJsonFile(String fileName, LootTableDataList data) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LootTableDataList.class, new LootTableSerializer());
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.disableHtmlEscaping();
		Gson gson = gsonBuilder.create();
		
		String jsonString = gson.toJson(data);
		
		try {
			Files.write(lootTablesPath().resolve(fileName + ".json"), jsonString.getBytes());
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public static Path lootTablesPath() {
		Path configPath = Constants.LOOT_TABLES_FOLDER_PATH;
    	if(!configPath.toFile().exists()) {
    		try {
    			Files.createDirectories(configPath);
    		} catch(IOException ex) { ex.printStackTrace(); }
    	}
    	return configPath;
	}
}
