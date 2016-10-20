package com.codexcrafts.wardcraft.block;

import com.codexcrafts.wardcraft.WardcraftMain;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockDebug extends Block{
	
	private static final String name = "block_debug";

	public BlockDebug() {
		super(Material.CLOTH);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(WardcraftMain.tab);
		GameRegistry.register(this);
		ItemBlock item = new ItemBlock(this);
		item.setRegistryName("block_debug");
		GameRegistry.register(item);
	}

}
