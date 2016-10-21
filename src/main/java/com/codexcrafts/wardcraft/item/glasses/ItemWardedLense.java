package com.codexcrafts.wardcraft.item.glasses;

import com.codexcrafts.wardcraft.WardcraftMain;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWardedLense extends Item{
	
	private static final String name = "item_warded_lense";
	
	public ItemWardedLense() {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(WardcraftMain.tab);
		setMaxStackSize(1);
		setRecipe();
		GameRegistry.register(this);
	}
	
	private void setRecipe(){
		ItemStack nugget = new ItemStack(Items.GOLD_NUGGET);
		ItemStack glass = new ItemStack(Blocks.GLASS);
		
		GameRegistry.addRecipe(new ItemStack(this), " x ", "xyx", " x ", 'x', nugget, 'y', glass);
	}

}