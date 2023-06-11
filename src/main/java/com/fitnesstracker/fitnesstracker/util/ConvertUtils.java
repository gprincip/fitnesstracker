package com.fitnesstracker.fitnesstracker.util;

import java.time.DayOfWeek;

import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.core.domain.WeightUnit;

public class ConvertUtils {

	public static WeightData oneLineToWeightData(String line) {
		
		if(!lineIsValidWeightData(line)) {
			return null;
		}
		
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
	
	private static boolean lineIsValidWeightData(String line) {
		
		//if line begins with weekday name, consider it valid
		if(line.startsWith(weekDayToText(DayOfWeek.MONDAY))
				|| line.startsWith(weekDayToText(DayOfWeek.TUESDAY))
				|| line.startsWith(weekDayToText(DayOfWeek.WEDNESDAY))
				|| line.startsWith(weekDayToText(DayOfWeek.THURSDAY))
				|| line.startsWith(weekDayToText(DayOfWeek.FRIDAY))
				|| line.startsWith(weekDayToText(DayOfWeek.SATURDAY))
				|| line.startsWith(weekDayToText(DayOfWeek.SUNDAY))) {
			return true;
		}
		
		return false;
	}

	public static String weekDayToText(DayOfWeek dayOfWeek) {
		switch(dayOfWeek) {
			case MONDAY : return "PON";
			case TUESDAY : return "UTO";
			case WEDNESDAY : return "SRE";
			case THURSDAY : return "CET";
			case FRIDAY : return "PET";
			case SATURDAY : return "SUB";
			case SUNDAY : return "NED";
			default : return "?";
		}
	}
	
}
