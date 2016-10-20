package com.codexcrafts.wardcraft.block;

import com.codexcrafts.wardcraft.WardcraftMain;
import com.codexcrafts.wardcraft.block.ward.BasicWard;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class BlockRenderRegistry {

	public static String modid = WardcraftMain.MODID;

	public static void registerBlockRenderer() {
		reg(BlockRegistry.debugBlock);
		//reg(BlockRegistry.testWardBlock);
		
		for(String ward : BlockRegistry.wards.keySet()){
			reg(BlockRegistry.wards.get(ward));
		}
	}

	public static void reg(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
