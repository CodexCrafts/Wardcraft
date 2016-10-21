package com.codexcrafts.wardcraft.item.glasses;

import com.codexcrafts.wardcraft.WardcraftMain;
import com.codexcrafts.wardcraft.block.ward.KeyWard;
import com.codexcrafts.wardcraft.block.ward.TileEntityKeyWard;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWardedFocus extends Item {

	private static final String name = "item_warded_focus";

	public ItemWardedFocus() {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(WardcraftMain.tab);
		setMaxStackSize(1);
		setRecipe();
		GameRegistry.register(this);
	}

	private void setRecipe() {
		ItemStack nugget = new ItemStack(Items.GOLD_NUGGET);
		ItemStack glass = new ItemStack(Blocks.GLASS);

		GameRegistry.addRecipe(new ItemStack(this), " x ", "xyx", " x ", 'x', nugget, 'y', glass);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (hand.equals(EnumHand.MAIN_HAND) && !worldIn.isRemote) {
			Block clicked = worldIn.getBlockState(pos).getBlock();
			if (clicked instanceof KeyWard) {
				if (stack.getTagCompound() == null) {
					stack.setTagCompound(new NBTTagCompound());
				}
				if (playerIn.isSneaking()) {
					stack.getTagCompound().setIntArray("generator", new int[] { pos.getX(), pos.getY(), pos.getZ() });
					displayMessageToPlayer(playerIn,
							"Setting generator to : " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()+".");
				} else {
					int[] coords = stack.getTagCompound().getIntArray("generator");
					if (coords != null) {
						BlockPos targetPos = new BlockPos(coords[0], coords[1], coords[2]);
						TileEntity te = worldIn.getTileEntity(targetPos);

						if (te instanceof TileEntityKeyWard) {
							((TileEntityKeyWard) te).setTarget(pos);
							displayMessageToPlayer(playerIn, "Linking Key Ward with generator.");
						}
					}
				}
			}
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	private void displayMessageToPlayer(EntityPlayer player, String msg) {
		player.addChatComponentMessage(new TextComponentString(msg));
	}
}