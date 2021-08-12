package com.imjustlazy.lootbags.config.json.tables;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

// A simple wrapper object for loot table data.
public class LootTableDataList {

	LootMetadata metadata;
	List<LootItemData> itemData;
	List<String> weightedItemData;
	
	public LootTableDataList() {
		this(new LootMetadata());
	}
	
	public LootTableDataList(LootMetadata metadata) {
		this(metadata, new ArrayList<>());
	}
	
	public LootTableDataList(LootMetadata metadata, List<LootItemData> dataList) {
		this.metadata = metadata;
		this.itemData = dataList;
		this.weightedItemData = generateWeightedItemData();
	}
	
	public List<LootItemData> getLootTableData() {
		return this.itemData;
	}
	
	// May return null if metadata is not present.
	public LootMetadata getMetadata() {
		return this.metadata;
	}
	
	public void setLootTableData(List<LootItemData> dataList) {
		this.itemData = dataList;
	}
	
	public void setMetadata(LootMetadata metadata) {
		this.metadata = metadata;
	}
	
	public void addItemData(LootItemData data) {
		this.itemData.add(data);
	}
	
	public void addEntityIDs(List<String> entityID) {
		this.metadata.getNpcIDs().addAll(entityID);
	}
	
	public void setDropOdds(double odds) {
		this.metadata.setLootOdds(odds);
	}
	
	public List<String> getWeightedItemData() {
		return this.weightedItemData;
	}
	
	public ArrayList<String> generateWeightedItemData() {
		ArrayList<String> result = Lists.newArrayList();
		for(LootItemData data : itemData) {
			for(int i = 0; i < data.getWeight(); i++)
				result.add(data.getItemName());
		}
		return result;
	}
	
}
