package com.codexcrafts.wardcraft.block;

import net.minecraft.block.Block;

public class BlockRegistry {
	
	public static Block debugBlock;
	
	public static void createBlocks(){
		debugBlock = new BlockDebug();
	}

}
