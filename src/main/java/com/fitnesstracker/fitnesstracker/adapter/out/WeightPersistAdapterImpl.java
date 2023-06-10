package com.fitnesstracker.fitnesstracker.adapter.out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;

import com.fitnesstracker.fitnesstracker.core.domain.Constants;
import com.fitnesstracker.fitnesstracker.core.domain.WeightData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeightPersistAdapterImpl implements WeightPersistAdapter{

	@Autowired
	WeightAnalyzerPort weightAnalyzer;
	
	@Override
	public void persistWeightData(WeightData data) {
		
		File weightsFile = new File(Constants.WEIGHTS_FILE_PATH);
		try (Writer writer = new FileWriter(weightsFile, true)){
			
			writeDataToFile(writer, data);
			
			if (data.getTimestamp().getDayOfWeek() == DayOfWeek.SUNDAY) {
				List<WeightData> currentWeekData = weightAnalyzer.extractCurrentWeeksData(weightsFile);
				drawLineAndAvgWeight(writer, currentWeekData);
				executeSundayReport(writer, currentWeekData);
			}
			
		} catch (Exception e) {
			log.error("Error during persisting weight data to a file.", e);
		}
		
	}

	private void writeDataToFile(Writer writer, WeightData data) {
		try {
			//TODO: don't add \n at the beginning of the line. If file is empty, this will create first empty line
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

	private void executeSundayReport(Writer writer, List<WeightData> currentWeekData) {
		// TODO Auto-generated method stub
		
	}

	private void drawLineAndAvgWeight(Writer writer, List<WeightData> currentWeekData) throws IOException {
		
		String avgWeight = new BigDecimal(calculateAverageWeight(currentWeekData)).setScale(2, RoundingMode.CEILING).toString();
		
		writer.append("\n")
			  .append(Constants.END_OF_WEEK_LINE)
			  .append(Constants.SPACE_AFTER_END_OF_WEEK_LINE)
			  .append(avgWeight);
	}
	
	private Double calculateAverageWeight(List<WeightData> data) {
		//TODO: Here data can be null if last line of the weight.txt file is a end of week line (===========)
		//handle that
		OptionalDouble avg = data.stream()
			.mapToDouble(WeightData::getWeight)
			.average();
		
		if(avg.isPresent()) {
			return avg.getAsDouble();
		}else {
			return null;
		}
		
	}

	
	
}
