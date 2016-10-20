package com.codexcrafts.wardcraft.block.ward;

import com.codexcrafts.wardcraft.block.BlockRegistry;
import com.codexcrafts.wardcraft.wardnets.IWardNet;
import com.codexcrafts.wardcraft.wardnets.WardNets;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class TileEntityKeyWard extends TileEntity implements ITickable {

	private int power;
	private Block corner1, corner2, corner3, corner4;
	private int counter = 0;

	public TileEntityKeyWard() {
		counter = 0;
	}

	@Override
	public void update() {
		if (counter > 20) {
			power = (getPower(worldObj, pos) * 4) + 4;
			Block corner1 = worldObj.getBlockState(pos.add(power, 0, power)).getBlock();
			Block corner2 = worldObj.getBlockState(pos.add(-power, 0, power)).getBlock();
			Block corner3 = worldObj.getBlockState(pos.add(-power, 0, -power)).getBlock();
			Block corner4 = worldObj.getBlockState(pos.add(power, 0, -power)).getBlock();

			boolean allCorners = corner1 instanceof BasicWard && corner2 instanceof BasicWard
					&& corner3 instanceof BasicWard && corner4 instanceof BasicWard;

			if (allCorners) {
				BasicWard[] corners = { (BasicWard) corner1, (BasicWard) corner2, (BasicWard) corner3,
						(BasicWard) corner4 };
				IWardNet something = WardNets.getWardNet(Arrays.asList(corners));
				if (something != null) {
					something.onUpdate(worldObj, pos, power);
				}
			}
			counter = 0;
		} else {
			counter++;
		}
	}

	private int getPower(World world, BlockPos pos) {
		int range = 1;
		int count = 0;

		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				BlockPos currentPos = pos.add(i, 0, j);
				if (world.getBlockState(currentPos).getBlock() == BlockRegistry.getWard(EnumWard.POSITIVE)) {
					count++;
				}
			}
		}
		return count;
	}

}
