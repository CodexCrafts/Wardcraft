package com.codexcrafts.wardcraft.wardnets.generators;

import java.util.ArrayList;
import java.util.List;

import com.codexcrafts.wardcraft.block.BlockRegistry;
import com.codexcrafts.wardcraft.block.ward.BasicWard;
import com.codexcrafts.wardcraft.block.ward.EnumWard;
import com.codexcrafts.wardcraft.block.ward.TileEntityKeyWard;
import com.codexcrafts.wardcraft.wardnets.IWardNet;
import com.codexcrafts.wardcraft.wardnets.IWardNetGenerator;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WardNetGenerator implements IWardNet, IWardNetGenerator {

	private static List<BasicWard> wards;

	@Override
	public List<BasicWard> getCorners() {

		if (wards == null) {
			wards = new ArrayList<BasicWard>();
			wards.add(BlockRegistry.getWard(EnumWard.FIRE));
			wards.add(BlockRegistry.getWard(EnumWard.EARTH));
			wards.add(BlockRegistry.getWard(EnumWard.FIRE));
			wards.add(BlockRegistry.getWard(EnumWard.EARTH));
		}

		return wards;
	}

	@Override
	public void onUpdate(World world, BlockPos pos, int power) {
		consumeFuel(world, pos, power);
		chargeTarget(world, pos);
	}

	@Override
	public int getUpdateCost() {
		return 0;
	}

	@Override
	public void consumeFuel(World world, BlockPos pos, int power) {
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
				new AxisAlignedBB(pos.add(power * -1, 0, power * -1), pos.add(power, 1, power)));
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityKeyWard) {
			for (EntityItem item : items) {
				if (TileEntityFurnace.isItemFuel(item.getEntityItem())) {
					((TileEntityKeyWard) te).addCharge(TileEntityFurnace.getItemBurnTime(item.getEntityItem()) / 100);
					if (item.getEntityItem().stackSize > 1) {
						item.getEntityItem().stackSize--;
					} else {
						world.removeEntity(item);
					}
				}
			}
		}
	}

	@Override
	public void chargeTarget(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityKeyWard) {
			BlockPos target = ((TileEntityKeyWard) te).getTarget();
			if (target != null) {
				TileEntity targetTE = world.getTileEntity(target);
				if (targetTE != null && targetTE instanceof TileEntityKeyWard) {
					int removed = ((TileEntityKeyWard) te).removeCharge(4);
					if (removed > 0) {
						((TileEntityKeyWard) targetTE).addCharge(removed);
					}
				} else {
					((TileEntityKeyWard) te).setTarget(null);
				}
			}
		}
	}

}
