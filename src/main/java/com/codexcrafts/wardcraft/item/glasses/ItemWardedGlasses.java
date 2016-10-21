package com.codexcrafts.wardcraft.item.glasses;

import com.codexcrafts.wardcraft.WardcraftMain;
import com.codexcrafts.wardcraft.item.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWardedGlasses extends ItemArmor{
	
	public static ArmorMaterial warded_glasses_material = EnumHelper.addArmorMaterial("warded_glasses", "wardcraft:warded_glasses", 7, new int[]{2, 5, 3, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f);
			
	private static final String name = "item_warded_glasses";
	
	public ItemWardedGlasses(int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(warded_glasses_material, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(WardcraftMain.tab);
		setRecipe();
		GameRegistry.register(this);
	}
	
	private void setRecipe(){
		ItemStack lense = new ItemStack(ItemRegistry.warded_lense);
		ItemStack nugget = new ItemStack(Items.GOLD_NUGGET);
		ItemStack glasses = new ItemStack(this);
		
		GameRegistry.addRecipe(glasses, "xyx", "   ", "   ", 'x', lense, 'y', nugget);
		GameRegistry.addRecipe(glasses, "   ", "xyx", "   ", 'x', lense, 'y', nugget);
		GameRegistry.addRecipe(glasses, "   ", "   ", "xyx", 'x', lense, 'y', nugget);
	}
		

}
