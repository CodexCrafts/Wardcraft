package com.codexcrafts.wardcraft.wardnets.generators;

import java.util.ArrayList;
import java.util.List;

import com.codexcrafts.wardcraft.block.BlockRegistry;
import com.codexcrafts.wardcraft.block.ward.BasicWard;
import com.codexcrafts.wardcraft.block.ward.EnumWard;
import com.codexcrafts.wardcraft.block.ward.TileEntityKeyWard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WardNetGeneratorEntity extends WardNetGenerator {

	private Class entityClass;
	private int fuelPerCycle, damagePerCycle;
	private EnumWard ward;

	private ArrayList<BasicWard> wards;

	public WardNetGeneratorEntity(Class entityClass, int fuelPerCycle, int damagePerCycle, EnumWard ward) {
		this.entityClass = entityClass;
		this.fuelPerCycle = fuelPerCycle;
		this.damagePerCycle = damagePerCycle;
		this.ward = ward;
		setupWards();
	}

	private void setupWards() {
		wards = new ArrayList<BasicWard>();
		wards.add(BlockRegistry.getWard(EnumWard.FIRE));
		wards.add(BlockRegistry.getWard(ward));
		wards.add(BlockRegistry.getWard(EnumWard.FIRE));
		wards.add(BlockRegistry.getWard(ward));
	}
	
	@Override
	public List<BasicWard> getCorners() {
		return wards;
	}
	
	@Override
	public void consumeFuel(World world, BlockPos pos, int power) {
		List entities = world.getEntitiesWithinAABB(entityClass, new AxisAlignedBB(pos.add(power * -1, 0, power * -1), pos.add(power, 1, power)));
		
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityKeyWard) {
			for (Object entity : entities) {
				if (entity instanceof Entity) {
					System.out.println("Adding charge");
					((TileEntityKeyWard) te).addCharge(fuelPerCycle);
					((Entity) entity).attackEntityFrom(DamageSource.magic, damagePerCycle);
					if(!(((Entity) entity).isEntityAlive())){
						world.removeEntity((Entity) entity);
					}
				}
			}
		}
	}

}
