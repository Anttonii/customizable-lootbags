package com.imjustlazy.lootbags.config.json.tables;

public class LootItemData {

	private final String itemName;
	private final int minCount;
	private final int maxCount;
	private final int weight;
	
	public LootItemData(String itemName, int minCount, int maxCount) {
		this.itemName = itemName;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.weight = 1;
	}
	
	public LootItemData(String itemName, int minCount, int maxCount, int weight) {
		this.itemName = itemName;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.weight = weight;
	}

	public String getItemName() {
		return itemName;
	}

	public int getMinCount() {
		return minCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public int getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return itemName + ", " + maxCount + ", " + minCount + ", " + weight;
	}
	
}
