package com.codexcrafts.wardcraft.item;

import com.codexcrafts.wardcraft.item.glasses.ItemWardedFocus;
import com.codexcrafts.wardcraft.item.glasses.ItemWardedGlasses;
import com.codexcrafts.wardcraft.item.glasses.ItemWardedLense;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

public class ItemRegistry {
	
	public static Item debugItem, warded_lense, warded_glasses, warded_focus;
	
	public static void createItems(){
		debugItem = new ItemDebug();
		warded_lense = new ItemWardedLense();
		warded_glasses = new ItemWardedGlasses(1, EntityEquipmentSlot.HEAD);
		warded_focus = new ItemWardedFocus();
	}

}
