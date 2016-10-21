package com.codexcrafts.wardcraft.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

public class ItemRegistry {
	
	public static Item debugItem, warded_glasses;
	
	public static void createItems(){
		debugItem = new ItemDebug();
		warded_glasses = new ItemWardedGlasses(1, EntityEquipmentSlot.HEAD);
	}

}
