package com.codexcrafts.wardcraft.wardnets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.codexcrafts.wardcraft.block.ward.BasicWard;
import com.codexcrafts.wardcraft.block.ward.EnumWard;
import com.codexcrafts.wardcraft.wardnets.consumers.WardNetTest;
import com.codexcrafts.wardcraft.wardnets.generators.WardNetGenerator;
import com.codexcrafts.wardcraft.wardnets.generators.WardNetGeneratorEntity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;

public class WardNets {
	
	private static final HashMap<List<BasicWard>, IWardNet> wardNets = createWardNets();

	private static HashMap<List<BasicWard>, IWardNet> createWardNets() {
		HashMap<List<BasicWard>, IWardNet> tempWardNets = new HashMap<List<BasicWard>, IWardNet>();
		IWardNet testWardNet = new WardNetTest();
		tempWardNets.put(testWardNet.getCorners(), testWardNet);
		
		IWardNet generatorWardNet = new WardNetGenerator();
		tempWardNets.put(generatorWardNet.getCorners(), generatorWardNet);
		
		addEntityGenerator(EntityMob.class, 1, 2, EnumWard.MOB, tempWardNets);
		addEntityGenerator(EntityAnimal.class, 1, 2, EnumWard.ANIMAL, tempWardNets);
		
		return tempWardNets;
	}
	
	private static void addEntityGenerator(Class entityClass, int fuelPerCycle, int damagePerCycle, EnumWard ward, HashMap<List<BasicWard>, IWardNet> tempWardNets){
		IWardNet entityWardNet = new WardNetGeneratorEntity(entityClass, fuelPerCycle, damagePerCycle, ward);
		tempWardNets.put(entityWardNet.getCorners(), entityWardNet);
	}
	
	public static IWardNet getWardNet(List<BasicWard> corners){
		for(List<BasicWard> wards: wardNets.keySet()){
			if(wards.size() == corners.size() && (wards.containsAll(corners) && corners.containsAll(wards))){
				return wardNets.get(wards);
			}
		}
		return null;
	}

}
