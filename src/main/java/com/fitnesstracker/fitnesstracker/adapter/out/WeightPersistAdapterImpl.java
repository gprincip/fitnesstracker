package com.fitnesstracker.fitnesstracker.adapter.out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;

import com.fitnesstracker.fitnesstracker.core.domain.Constants;
import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.util.ConvertUtils;
import com.fitnesstracker.fitnesstracker.util.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeightPersistAdapterImpl implements WeightPersistAdapter{

	@Autowired
	WeightAnalyzerPort weightAnalyzer;
	
	@Override
	public void persistWeightData(WeightData data) {
		
		File weightsFile = new File(Constants.WEIGHTS_FILE_PATH);
		try (Writer writer = new FileWriter(weightsFile, true)){
			
			writeDataToFile(writer, data, FileUtils.readTextFromFile(weightsFile));
			
			if (isNewWeek(data)) {
				List<WeightData> lastWeekData = weightAnalyzer.extractLastWeeksData(weightsFile);
				drawLineAndAvgWeight(writer, lastWeekData);
				executeSundayReport(writer, lastWeekData);
			}
			
		} catch (Exception e) {
			log.error("Error during persisting weight data to a file.", e);
		}
		
	}

	/**
	 * @param lastWeekData 
	 * @return true if current day is the week after the previous entry's week
	 */
	private boolean isNewWeek(WeightData lastWeekData) {
		//TODO: Optimize this logic so it's not strictly working with SUNDAY
		return (lastWeekData.getTimestamp().getDayOfWeek() == DayOfWeek.SUNDAY);
	}

	private void writeDataToFile(Writer writer, WeightData data, List<String> text) {
		try {
			
			if(getLastNotEmptyLine(text).contains(Constants.END_OF_WEEK_LINE)) {
				writer.append("\n")
					  .append(LocalDateTime.now().format(Constants.DATE_FORMAT));
			}
			
			//TODO: don't add \n at the beginning of the line. If file is empty, this will create first empty line
			writer.append("\n" + ConvertUtils.weekDayToText(data.getTimestamp().getDayOfWeek()));
			writer.append(" " + data.getWeight() + data.getWeightUnit().name());
		} catch (IOException e) {
			log.error("Error writing to a file", e);
		}
	}

	private String getLastNotEmptyLine(List<String> list) {
		
		for(int i = list.size() - 1; i >= 0; i--) {
			if(list.get(i).length() > 0) {
				return list.get(i);
			}
		}
		
		return "";
	}

	private void executeSundayReport(Writer writer, List<WeightData> currentWeekData) {
		// TODO Auto-generated method stub
		
	}

	private void drawLineAndAvgWeight(Writer writer, List<WeightData> currentWeekData) throws IOException {
		
		String avgWeight = new BigDecimal(calculateAverageWeight(currentWeekData))
				.setScale(2, RoundingMode.CEILING)
				.toString();
		
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
