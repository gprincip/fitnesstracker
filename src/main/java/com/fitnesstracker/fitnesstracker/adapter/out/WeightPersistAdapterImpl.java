package com.fitnesstracker.fitnesstracker.adapter.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.DayOfWeek;
import java.util.Scanner;

import com.fitnesstracker.fitnesstracker.core.domain.WeightData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeightPersistAdapterImpl implements WeightPersistAdapter{

	@Override
	public void persistWeightData(WeightData data) {
		
		File weightsFile = new File("weight.txt");
		try (Writer writer = new FileWriter(weightsFile)){
			
			writeDataToFile(writer, data);
			
			if (data.getTimestamp().getDayOfWeek() == DayOfWeek.SUNDAY) {
				drawLine();
				executeSundayReport();
			}
			
		} catch (Exception e) {
			log.error("Error during persisting weight data to a file.", e);
		}
		
	}

	private void writeDataToFile(Writer writer, WeightData data) {
		try {
			writer.append("\n" + weekDayToText(data.getTimestamp().getDayOfWeek()));
			writer.append(" " + data.getWeight() + data.getWeightUnit().name());
		} catch (IOException e) {
			log.error("Error writing to a file", e);
		}
	}

	private CharSequence weekDayToText(DayOfWeek dayOfWeek) {
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

	private void executeSundayReport() {
		// TODO Auto-generated method stub
		
	}

	private void drawLine() {
		// TODO Auto-generated method stub
		
	}

	
	
}
