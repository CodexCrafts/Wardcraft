package com.codexcrafts.wardcraft;

import com.codexcrafts.wardcraft.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = WardcraftMain.MODID, version = WardcraftMain.VERSION)
public class WardcraftMain {
	public static final String MODID = "wardcraft";
	public static final String VERSION = "0.1";
	
	@Instance(value=WardcraftMain.MODID)
	public static WardcraftMain instance;
	
	@SidedProxy(clientSide="com.codexcrafts.wardcraft.proxy.ClientProxy", serverSide="com.codexcrafts.wardcraft.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabs tab = createTab();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	    this.proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	    this.proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    this.proxy.postInit(e);
	}
	
	private static CreativeTabs createTab(){
		return new CreativeTabs("wardcraft_tab") {
			
			@Override
			public Item getTabIconItem() {
				return Items.APPLE;
			}
		};
	}
}
