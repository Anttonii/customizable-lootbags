package com.imjustlazy.lootbags.config.json.tables;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.imjustlazy.lootbags.Constants;
import com.imjustlazy.lootbags.Lootbags;

public class LootTableSerializer implements JsonSerializer<LootTableDataList>, JsonDeserializer<LootTableDataList> {

	@Override
	public JsonElement serialize(LootTableDataList src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject table = new JsonObject();
		JsonObject metadata = new JsonObject();
		
		LootMetadata lootMetadata = src.getMetadata();
		if(lootMetadata == null) {
			Lootbags.LOGGER.warn("No metadata found, serialization failed.");
			return table;			
		}
		
		if(src.getLootTableData().isEmpty()) {
			Lootbags.LOGGER.warn("Empty data list, serialization failed.");
			return table;
		}
		
		String itemID = lootMetadata.getItemID();
		List<String> npcIDs = lootMetadata.getNpcIDs();
		double odds = lootMetadata.getLootOdds();
		int rolls = lootMetadata.getRollsCount();
		
		JsonArray npcIDArray = new JsonArray();
		npcIDs.stream().forEach((id) -> npcIDArray.add(id));
		
		metadata.addProperty(Constants.PROPERTY_ITEM_ID, itemID);
		metadata.add(Constants.PROPERTY_NPC_IDS, npcIDArray);
		metadata.addProperty(Constants.PROPERTY_DROP_ODDS, odds);
		metadata.addProperty(Constants.PROPERTY_ROLLS, rolls);
		
		JsonObject items = new JsonObject();
		for(LootItemData tableData : src.getLootTableData()) {
			JsonObject item = new JsonObject();
			
			item.addProperty(Constants.PROPERTY_MAX, tableData.getMaxCount());
			item.addProperty(Constants.PROPERTY_MIN, tableData.getMinCount());
			item.addProperty(Constants.PROPERTY_WEIGHT, tableData.getWeight());
		
			items.add(tableData.getItemName(), item);
		}
		
		table.add(Constants.PROPERTY_METADATA, metadata);
		table.add(Constants.PROPERTY_ITEMS, items);
		
		Lootbags.LOGGER.info("Succesfully serialized lootbag json file with item id " + itemID);
		
		return table;
	}

	@Override
	public LootTableDataList deserialize(JsonElement elem, Type typeOf, JsonDeserializationContext ctx) throws JsonParseException {
		JsonObject table = elem.getAsJsonObject();
		
		Optional<LootMetadata> metadata = Optional.empty();
		List<LootItemData> itemData = new ArrayList<>();
		
		JsonObject metadataElem = table.get(Constants.PROPERTY_METADATA).getAsJsonObject();
		
		String itemID = metadataElem.get(Constants.PROPERTY_ITEM_ID).getAsString();
		double odds = metadataElem.get(Constants.PROPERTY_DROP_ODDS).getAsDouble();
		ArrayList<String> npcIDs = new ArrayList<>();
		metadataElem.get(Constants.PROPERTY_NPC_IDS).getAsJsonArray().forEach((member) -> npcIDs.add(member.getAsString()));
		int rolls = metadataElem.get(Constants.PROPERTY_ROLLS).getAsInt();
		
		metadata = Optional.of(new LootMetadata(itemID, npcIDs, odds, rolls));
		
		Set<Entry<String, JsonElement>> items = table.get(Constants.PROPERTY_ITEMS).getAsJsonObject().entrySet();
		for(Map.Entry<String, JsonElement> entry : items) {
			String item = entry.getKey();
			JsonObject element = entry.getValue().getAsJsonObject();
			
			int maxCount = element.get(Constants.PROPERTY_MAX).getAsInt();
			int minCount = element.get(Constants.PROPERTY_MIN).getAsInt();
			int weight = element.get(Constants.PROPERTY_WEIGHT).getAsInt();
			
			itemData.add(new LootItemData(item, minCount, maxCount, weight));
		}
		
		if(itemData.isEmpty() || !metadata.isPresent()) {
			Lootbags.LOGGER.warn("Failed to deserialize, data set empty.");
			return new LootTableDataList();			
		} else
			return new LootTableDataList(metadata.get(), itemData);
	}
	
	
}
