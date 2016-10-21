package com.codexcrafts.wardcraft.wardnets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codexcrafts.wardcraft.block.BlockRegistry;
import com.codexcrafts.wardcraft.block.ward.BasicWard;
import com.codexcrafts.wardcraft.block.ward.EnumWard;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WardNetTest implements IWardNet {

	private static List<BasicWard> wards;

	@Override
	public List<BasicWard> getCorners() {

		if (wards == null) {
			wards = new ArrayList<BasicWard>();
			wards.add(BlockRegistry.getWard(EnumWard.AIR));
			wards.add(BlockRegistry.getWard(EnumWard.AIR));
			wards.add(BlockRegistry.getWard(EnumWard.AIR));
			wards.add(BlockRegistry.getWard(EnumWard.AIR));
		}

		return wards;
	}

	@Override
	public void onUpdate(World world, BlockPos pos, int power) {
		System.out.println("Updating wardnet");
	}

	@Override
	public int getUpdateCost() {
		return 2;
	}

}
