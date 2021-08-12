package com.imjustlazy.lootbags.items;

import com.imjustlazy.lootbags.Constants;
import com.imjustlazy.lootbags.Lootbags;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;

public class ItemLootbag extends Item {

	private Rarity UNCOMMON = Rarity.create("lootbag_uncommon", TextFormatting.GREEN);
	private Rarity LEGENDARY = Rarity.create("lootbag_legendary", TextFormatting.YELLOW);
	
	public ItemLootbag(String name) {
		super(new Item.Properties().tab(Lootbags.TAB_ITEMS));
		this.setRegistryName(Lootbags.MOD_ID, name);
	}
	
	@Override
	public Rarity getRarity(ItemStack stack) {
		switch(this.getRegistryName().getPath()) {
		case Constants.UNCOMMON_LOOTBAG: return UNCOMMON;
		case Constants.RARE_LOOTBAG: return Rarity.RARE;
		case Constants.EPIC_LOOTBAG: return Rarity.EPIC;
		case Constants.LEGENDARY_LOOTBAG: return LEGENDARY;
		default: return Rarity.COMMON;
		}
	}
	
}
