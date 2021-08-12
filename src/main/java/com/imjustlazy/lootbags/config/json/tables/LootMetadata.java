package com.imjustlazy.lootbags.config.json.tables;

import java.util.ArrayList;
import java.util.List;

// Represents metadata related information.
public class LootMetadata {

	private String itemID;
	
	// List of npc IDs that drop this lootbag.
	private List<String> npcIDs;
	
	// Number between 0.0 and 1.0
	private double lootOdds;
	
	// How many rolls of loot a lootbag has.
	private int rolls;
	
	public LootMetadata() {
		this("");
	}
	
	// No NPC metadata constructor
	public LootMetadata(String itemID) {
		this.itemID = itemID;
		this.npcIDs = new ArrayList<String>();
		this.lootOdds = 0.0;
		this.rolls = 1;
	}
	
	public LootMetadata(String itemID, List<String> npcIDs, double lootOdds, int rolls) {
		this.itemID = itemID;
		this.npcIDs = npcIDs;
		this.lootOdds = lootOdds;
		this.rolls = rolls;
	}

	public String getItemID() {
		return itemID;
	}

	public List<String> getNpcIDs() {
		return npcIDs;
	}

	public double getLootOdds() {
		return lootOdds;
	}
	
	public int getRollsCount() {
		return rolls;
	}
	
	public void setLootOdds(double lootOdds) {
		this.lootOdds = lootOdds;
	}
	
}
