package com.codexcrafts.wardcraft.item.glasses;

import com.codexcrafts.wardcraft.WardcraftMain;
import com.codexcrafts.wardcraft.item.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWardedLense extends Item {

	private static final String name = "item_warded_lense";

	public ItemWardedLense() {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(WardcraftMain.tab);
		setMaxStackSize(1);
		setRecipe();
		GameRegistry.register(this);
	}

	private void setRecipe() {
		ItemStack lense = new ItemStack(ItemRegistry.warded_lense);
		ItemStack nugget = new ItemStack(Items.GOLD_NUGGET);

		GameRegistry.addRecipe(new ItemStack(this), " x ", "xyx", " x ", 'x', nugget, 'y', lense);
	}
}