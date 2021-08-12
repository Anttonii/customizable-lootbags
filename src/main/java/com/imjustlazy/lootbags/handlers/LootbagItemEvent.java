package com.imjustlazy.lootbags.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.imjustlazy.lootbags.Lootbags;
import com.imjustlazy.lootbags.client.RewardsScreen;
import com.imjustlazy.lootbags.config.CommonConfig;
import com.imjustlazy.lootbags.config.json.tables.LootItemData;
import com.imjustlazy.lootbags.config.json.tables.LootMetadata;
import com.imjustlazy.lootbags.config.json.tables.LootTableDataList;
import com.imjustlazy.lootbags.items.ItemLootbag;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class LootbagItemEvent {

	@SubscribeEvent
	public void onRightClick(RightClickItem event) {
		if(!event.getItemStack().isEmpty()) {
			if(event.getItemStack().getItem() instanceof ItemLootbag) {
				openLootbag(event.getPlayer(), event.getItemStack());
			}
		}
	}
	
	private void openLootbag(PlayerEntity player, ItemStack item) {
		World world = player.level;
		
		if(!world.isClientSide()) {
			if(!player.isCreative())
				player.getMainHandItem().shrink(1);

			ArrayList<ItemStack> items = generateRoll(item);

			if(!player.isSteppingCarefully() && CommonConfig.ENABLE_REWARD_SCREEN.get())
				Minecraft.getInstance().setScreen(new RewardsScreen(items));
			
			if(!items.isEmpty()) {
				for(ItemStack s : items) {
					if(player.inventory.getFreeSlot() < 0) {
						ItemEntity toSpawn = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), s.copy());
						world.addFreshEntity(toSpawn);
					} else
						player.addItem(s.copy());
				}
			}
		}
	}
	
	private ArrayList<ItemStack> generateRoll(ItemStack itemStack) {
		ArrayList<ItemStack> result = Lists.newArrayList();
		Random random = new Random();
		String lootbagItem = itemStack.getItem().getRegistryName().getPath();
		
		LootTableDataList data = LootbagDataRegistry.get(lootbagItem);
		
		List<LootItemData> itemData = data.getLootTableData();
		List<String> weightedItemData = data.getWeightedItemData();
		LootMetadata metadata = data.getMetadata();
		
		for(int i = 0; i < metadata.getRollsCount(); i++) {
			int index = random.nextInt(weightedItemData.size());
			String itemID = weightedItemData.get(index);
			
			LootItemData item = itemData.stream().filter(lambda -> lambda.getItemName().equals(itemID)).findFirst().get();
			
			int count = item.getMinCount() + random.nextInt(item.getMaxCount() - item.getMinCount());
			Item itemObj = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item.getItemName()));
			
			if(itemObj == null) {
				Lootbags.LOGGER.error("Unable to find item from registry with item name:" + item.getItemName());
			} else {
				result.add(new ItemStack(itemObj, count));				
			}
		}
		
		return result;
	}
	
}
