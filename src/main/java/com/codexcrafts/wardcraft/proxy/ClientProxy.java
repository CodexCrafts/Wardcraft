package com.codexcrafts.wardcraft.proxy;

import com.codexcrafts.wardcraft.block.BlockRenderRegistry;
import com.codexcrafts.wardcraft.item.ItemRenderRegistry;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ItemRenderRegistry.registerItemRenderer();
        BlockRenderRegistry.registerBlockRenderer();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
