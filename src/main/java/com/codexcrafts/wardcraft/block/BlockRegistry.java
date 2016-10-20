package com.codexcrafts.wardcraft.block;

import java.util.HashMap;

import com.codexcrafts.wardcraft.block.ward.BasicWard;
import com.codexcrafts.wardcraft.block.ward.EnumWard;
import com.codexcrafts.wardcraft.block.ward.KeyWard;

import net.minecraft.block.Block;

public class BlockRegistry {
	
	public static Block debugBlock, testWardBlock;
	public static HashMap<String, BasicWard> wards;
	public static KeyWard key;
	
	public static void createBlocks(){
		debugBlock = new BlockDebug();
		//testWardBlock = new BasicWard("test");
		
		wards = new HashMap<String, BasicWard>();
		
		for(EnumWard ward : EnumWard.values()){
			wards.put(ward.toString().toLowerCase(), new BasicWard(ward));
		}
		
		key = new KeyWard("key");
	}
	
	public static BasicWard getWard(EnumWard ward){
		return wards.get(ward.toString().toLowerCase());
	}

}
