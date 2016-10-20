package com.codexcrafts.wardcraft.wardnets;

import java.util.ArrayList;
import java.util.List;

import com.codexcrafts.wardcraft.block.BlockRegistry;
import com.codexcrafts.wardcraft.block.ward.BasicWard;
import com.codexcrafts.wardcraft.block.ward.EnumWard;
import com.codexcrafts.wardcraft.block.ward.TileEntityKeyWard;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WardNetGenerator implements IWardNet {

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
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
				new AxisAlignedBB(pos.add(power * -1, 0, power * -1), pos.add(power, 1, power)));

		for (EntityItem item : items) {
			if (TileEntityFurnace.isItemFuel(item.getEntityItem())) {
				TileEntity te = world.getTileEntity(pos);

				if (te != null && te instanceof TileEntityKeyWard) {
					((TileEntityKeyWard) te).addCharge(TileEntityFurnace.getItemBurnTime(item.getEntityItem())/100);
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
	public int getUpdateCost() {
		return 0;
	}

}
