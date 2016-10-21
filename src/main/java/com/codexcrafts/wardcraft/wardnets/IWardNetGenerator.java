package com.codexcrafts.wardcraft.wardnets;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWardNetGenerator {
	
	public void consumeFuel(World world, BlockPos pos, int power);
	public void chargeTarget(World world, BlockPos pos);

}
