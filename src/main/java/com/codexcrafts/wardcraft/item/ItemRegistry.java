package com.codexcrafts.wardcraft.item;

import net.minecraft.item.Item;

public class ItemRegistry {
	
	public static Item debugItem;
	
	public static void createItems(){
		debugItem = new ItemDebug();
	}

}
