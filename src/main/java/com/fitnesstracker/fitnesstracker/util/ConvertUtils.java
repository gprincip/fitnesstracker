package com.fitnesstracker.fitnesstracker.util;

import java.time.DayOfWeek;

import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.core.domain.WeightUnit;

public class ConvertUtils {

	public static WeightData oneLineToWeightData(String line) {
		
		if(!lineIsValidWeightData(line)) {
			return null;
		}
		
		int startIndexOfWeight = line.split(" ").length == 3 ? 9 : 4;
		
		String strippedLine = line.substring(startIndexOfWeight);
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
		data.setDayOfWeek(srbTextToDayOfWeek(line.subSequence(0, 3).toString()));

		return data;
		
	}
	
	private static boolean lineIsValidWeightData(String line) {
		
		//if line begins with weekday name, consider it valid
		if(line.startsWith(weekDayToSrbText(DayOfWeek.MONDAY))
				|| line.startsWith(weekDayToSrbText(DayOfWeek.TUESDAY))
				|| line.startsWith(weekDayToSrbText(DayOfWeek.WEDNESDAY))
				|| line.startsWith(weekDayToSrbText(DayOfWeek.THURSDAY))
				|| line.startsWith(weekDayToSrbText(DayOfWeek.FRIDAY))
				|| line.startsWith(weekDayToSrbText(DayOfWeek.SATURDAY))
				|| line.startsWith(weekDayToSrbText(DayOfWeek.SUNDAY))) {
			return true;
		}
		
		return false;
	}

	public static String weekDayToSrbText(DayOfWeek dayOfWeek) {
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
	
	public static DayOfWeek srbTextToDayOfWeek(String text) {
		switch(text) {
			case "PON" : return DayOfWeek.MONDAY;
			case "UTO" : return DayOfWeek.TUESDAY;
			case "SRE" : return DayOfWeek.WEDNESDAY;
			case "CET" : return DayOfWeek.THURSDAY;
			case "PET" : return DayOfWeek.FRIDAY;
			case "SUB" : return DayOfWeek.SATURDAY;
			case "NED" : return DayOfWeek.SUNDAY;
			default : return null;
		}
	}
	
}
