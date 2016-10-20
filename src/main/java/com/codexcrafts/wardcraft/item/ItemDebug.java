package com.codexcrafts.wardcraft.item;

import com.codexcrafts.wardcraft.WardcraftMain;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemDebug extends Item{
	
	private static final String name = "item_debug";
	
	public ItemDebug() {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(WardcraftMain.tab);
		setMaxStackSize(1);
		GameRegistry.register(this);
	}

}
