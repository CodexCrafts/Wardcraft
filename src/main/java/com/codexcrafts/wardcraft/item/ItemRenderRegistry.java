package com.codexcrafts.wardcraft.item;

import com.codexcrafts.wardcraft.WardcraftMain;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRenderRegistry {

	public static String modid = WardcraftMain.MODID;

	public static void registerItemRenderer() {
		reg(ItemRegistry.debugItem);
		reg(ItemRegistry.warded_glasses);
		reg(ItemRegistry.warded_lense);
	}

	public static void reg(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
