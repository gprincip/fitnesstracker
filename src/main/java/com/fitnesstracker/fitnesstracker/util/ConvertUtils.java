package com.fitnesstracker.fitnesstracker.util;

import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.core.domain.WeightUnit;

public class ConvertUtils {

	public static WeightData oneLineToWeightData(String line) {
		//SUB 11.0KG
		
		String strippedLine = line.substring(4);
		WeightUnit weightUnit = null;
		for(WeightUnit unit : WeightUnit.values()) {
			if(strippedLine.contains(unit.name())) {
				strippedLine = strippedLine.replace(unit.name(), "");
				weightUnit = unit; //discovered weight unit used in line of text
			}
		}
		
		WeightData data = new WeightData();
		data.setWeightUnit(weightUnit);
		data.setWeight(Double.parseDouble(strippedLine));
		return data;
		
	}
	
}
