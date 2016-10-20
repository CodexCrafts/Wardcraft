package com.codexcrafts.wardcraft.wardnets;

import java.util.List;

import com.codexcrafts.wardcraft.block.ward.BasicWard;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWardNet {
	
	public List<BasicWard> getCorners();
	public void onUpdate(World world, BlockPos pos, int power);

}
