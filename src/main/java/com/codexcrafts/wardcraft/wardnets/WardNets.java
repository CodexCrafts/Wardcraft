package com.codexcrafts.wardcraft.wardnets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.codexcrafts.wardcraft.block.ward.BasicWard;

public class WardNets {
	
	private static final HashMap<List<BasicWard>, IWardNet> wardNets = createWardNets();

	private static HashMap<List<BasicWard>, IWardNet> createWardNets() {
		HashMap<List<BasicWard>, IWardNet> tempWardNets = new HashMap<List<BasicWard>, IWardNet>();
		IWardNet testWardNet = new WardNetTest();
		tempWardNets.put(testWardNet.getCorners(), testWardNet);
		
		IWardNet generatorWardNet = new WardNetGenerator();
		tempWardNets.put(generatorWardNet.getCorners(), generatorWardNet);
		return tempWardNets;
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
