package com.imjustlazy.lootbags.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.imjustlazy.lootbags.Constants;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class RewardsScreen extends Screen {

	// TODO: Implement this functionality when there are more than 5 items.
	// Maximum amount of items set to 5 per row.
	@SuppressWarnings("unused")
	private final int ITEMS_PER_ROW = 5;
	
	// Maximum rows set to 3.
	@SuppressWarnings("unused")
	private final int MAXIMUM_ROWS = 3;
	
	// Defines the scale at which items are rendered.
	private float scalarX = 2.0f;
	private float scalarY = 2.0f;

	private static final int TITLE_HEIGHT = 8;
	private List<ItemStack> renderItems = Lists.newArrayList();
	
	public RewardsScreen() {
		this(new ArrayList<ItemStack>());
	}
	
	public RewardsScreen(List<ItemStack> itemStacks) {
		this(new TranslationTextComponent(Constants.REWARDS_SCREEN_TITLE));
		this.renderItems = itemStacks;
	}
	
	protected RewardsScreen(ITextComponent title) {
		super(title);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		
		drawCenteredString(stack, this.font, this.title.getString(), (this.width / 2) - 4, TITLE_HEIGHT, 0xFFFFFF);
		renderItems(stack, mouseX, mouseY);
		
		super.render(stack, mouseX, mouseY, partialTicks);
	}
	
	public void renderItems(MatrixStack stack, int mouseX, int mouseY) {
		if(this.renderItems.isEmpty())
			return;
		
		int x = (int) ((this.width / 2) - ((this.renderItems.size() * (32 * scalarX)) / 2));
		int y = (this.height - 16) / 2;
		
		for(ItemStack item : this.renderItems) {
			RenderSystem.pushMatrix();
			RenderSystem.scalef(scalarX, scalarY, 1.0f);
			RenderSystem.translatef((float) (-x / scalarX), (float) (-y / scalarY), 0.0f);
			itemRenderer.renderAndDecorateItem(item, x, y);
			RenderSystem.popMatrix();
			
			List<ITextComponent> tooltipList = Lists.newArrayList();
			tooltipList.add(item.getDisplayName());
			tooltipList.add(new StringTextComponent("Count: " + item.getCount()));
			
			if(mouseX >= x && mouseY >= y && mouseX < x + (32 * scalarX) && mouseY < y + (32 * scalarY))
				GuiUtils.drawHoveringText(stack, tooltipList, x - 8, y + 50, this.width, this.height, 240, this.font);
			
			x = (int) (x + (32 * scalarX));
		}
	}
}
