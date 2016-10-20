package com.codexcrafts.wardcraft.block.ward;

import com.codexcrafts.wardcraft.block.BlockRegistry;
import com.codexcrafts.wardcraft.wardnets.IWardNet;
import com.codexcrafts.wardcraft.wardnets.WardNets;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class TileEntityKeyWard extends TileEntity implements ITickable {

	private int power;
	private Block corner1, corner2, corner3, corner4;
	private int counter = 0;
	private int charge;

	public TileEntityKeyWard() {
		counter = 0;
	}

	@Override
	public void update() {
		if (counter > 20 && !worldObj.isRemote) {
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
				IWardNet wardNet = WardNets.getWardNet(Arrays.asList(corners));
				if (wardNet != null && charge >= wardNet.getUpdateCost()) {
					wardNet.onUpdate(worldObj, pos, power);
					charge -= wardNet.getUpdateCost();
				}
			}
			counter = 0;
			System.out.println(charge);
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
	
	public void addCharge(int amount){
		this.charge += amount;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("charge", charge);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.charge = compound.getInteger("charge");
	}

}
