package com.imjustlazy.lootbags;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.imjustlazy.lootbags.common.NPCDropHandler;
import com.imjustlazy.lootbags.config.CommonConfig;
import com.imjustlazy.lootbags.config.json.LootbagJsonHandler;
import com.imjustlazy.lootbags.handlers.LootbagDataRegistry;
import com.imjustlazy.lootbags.handlers.LootbagItemEvent;
import com.imjustlazy.lootbags.items.LootbagItemRegistry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Lootbags.MOD_ID)
public class Lootbags
{
	public static final String MOD_ID = "lootbags";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ItemGroup TAB_ITEMS = new ItemGroup(MOD_ID) {
    	private Style style = Style.EMPTY.withColor(TextFormatting.YELLOW);
    	private IFormattableTextComponent textComponent = new TranslationTextComponent("itemGroup." + MOD_ID).setStyle(style);
    	
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(LootbagItemRegistry.LEGENDARY_LOOTBAG);
		}
		
		@Override
		public ITextComponent getDisplayName() {
			return textComponent;
		}
    };
    
    public Lootbags() {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	CommonConfig.setup(configFolderPath().resolve(Constants.CONFIG_COMMON_NAME + ".toml"));
    	
    	// TODO: Add kubejs support for custom lootbags to be added to the registry.
    	for(String lootbag : LootbagDataRegistry.getPreInit()) {
    		LootbagJsonHandler.handleJsonFile(lootbag);
    	}
    	
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private final Path configFolderPath() {
    	Path configPath = Constants.CONFIG_FOLDER_PATH;
    	if(!configPath.toFile().exists()) {
    		try {
    			Files.createDirectories(configPath);
    		} catch(IOException ex) { ex.printStackTrace(); }
    	}
    	return configPath;
    }
    
    private void setup(final FMLCommonSetupEvent event) {
    	MinecraftForge.EVENT_BUS.register(new LootbagItemEvent());
    	MinecraftForge.EVENT_BUS.register(new NPCDropHandler());
    }
}
