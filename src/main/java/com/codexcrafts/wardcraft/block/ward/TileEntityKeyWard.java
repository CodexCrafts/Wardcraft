package com.codexcrafts.wardcraft.block.ward;

import com.codexcrafts.wardcraft.block.BlockRegistry;
import com.codexcrafts.wardcraft.item.ItemRegistry;
import com.codexcrafts.wardcraft.particle.ParticleWardNet;
import com.codexcrafts.wardcraft.wardnets.IWardNet;
import com.codexcrafts.wardcraft.wardnets.WardNets;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

public class TileEntityKeyWard extends TileEntity implements ITickable {

	private BlockPos target;
	private int power;
	private Block corner1, corner2, corner3, corner4;
	private int counter = 0;
	private int charge;
	private IWardNet wardNet;

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
				wardNet = WardNets.getWardNet(Arrays.asList(corners));
				if (!worldObj.isRemote) {
					if (wardNet != null && charge >= wardNet.getUpdateCost()) {
						wardNet.onUpdate(worldObj, pos, power);
						charge -= wardNet.getUpdateCost();
					}
				}
				counter = 0;
			}

			if (worldObj.isRemote) {
				ItemStack helmetArmor = Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3);
				if (helmetArmor != null && helmetArmor.getItem().equals(ItemRegistry.warded_glasses)) {
					int particleRange = power * 2 + 1;
					randomlySpawnParticlesAtCorner(pos.add(power, 0, power), 1, particleRange * -1 + 1, 0,
							particleRange, 0.3f);
					randomlySpawnParticlesAtCorner(pos.add(power, 0, power), particleRange * -1 + 1, 1, particleRange,
							0, 0.3f);
					randomlySpawnParticlesAtCorner(pos.add(power * -1, 0, power * -1), 0, particleRange, 0,
							particleRange * -1, 0.3f);
					randomlySpawnParticlesAtCorner(pos.add(power * -1, 0, power * -1), particleRange, 0,
							particleRange * -1, 0, 0.3f);
					randomlySpawnParticlesAtCorner(pos.add(power * -1, 0, power), 0, particleRange * -1 + 1, 0,
							particleRange, 0.3f);
					randomlySpawnParticlesAtCorner(pos.add(power * -1, 0, power), particleRange, 1, particleRange * -1,
							0, 0.3f);
					randomlySpawnParticlesAtCorner(pos.add(power, 0, power * -1), particleRange * -1 + 1, 0,
							particleRange, 0, 0.3f);
					randomlySpawnParticlesAtCorner(pos.add(power, 0, power * -1), 1, particleRange, 0,
							particleRange * -1, 0.3f);

					if (target != null) {
						BlockPos dir = pos.subtract(target);
						spawnParticlesAtCorner(target, 0.5f, 0.5f, dir.getX(), dir.getZ());
					}
				}
			}
		} else {
			counter++;
		}
	}

	private void randomlySpawnParticlesAtCorner(BlockPos pos1, float xOffset, float zOffset, int xSpeed, int zSpeed,
			float chance) {
		if (chance > worldObj.rand.nextFloat()) {
			spawnParticlesAtCorner(pos1, xOffset, zOffset, xSpeed, zSpeed);
		}
	}

	private void spawnParticlesAtCorner(BlockPos pos1, float xOffset, float zOffset, int xSpeed, int zSpeed) {
		ParticleWardNet particle = new ParticleWardNet(worldObj, pos1.getX() + xOffset,
				pos1.getY() + worldObj.rand.nextFloat(), pos1.getZ() + zOffset, xSpeed, 0, zSpeed);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);

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

	public void addCharge(int amount) {
		this.charge += amount;
	}

	public int removeCharge(int amount) {
		if (amount <= charge) {
			charge -= amount;
			return amount;
		}
		int tempCharge = charge;
		charge = 0;
		return tempCharge;
	}

	public void setTarget(BlockPos target) {
		this.target = target;
	}

	public BlockPos getTarget() {
		return this.target;
	}

	public IWardNet getWardNet() {
		return wardNet;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("charge", charge);
		if (this.target != null) {
			compound.setIntArray("target", new int[] { target.getX(), target.getY(), target.getZ() });
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.charge = compound.getInteger("charge");
		int[] coords = compound.getIntArray("target");
		if (coords.length > 0) {
			this.target = new BlockPos(coords[0], coords[1], coords[2]);
		}
	}

//	@SideOnly(Side.SERVER)
//	@Override
//	public SPacketUpdateTileEntity getUpdatePacket() {
//		if (target != null) {
//			NBTTagCompound comp = new NBTTagCompound();
//			return new SPacketUpdateTileEntity(pos, getBlockMetadata(), writeToNBT(comp));
//		}
//		return super.getUpdatePacket();
//	}
//	
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
//		NBTTagCompound comp = pkt.getNbtCompound();
//		readFromNBT(comp);
//	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound comp = super.getUpdateTag();
		return writeToNBT(comp);
	}

}
