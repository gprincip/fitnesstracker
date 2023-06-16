package com.fitnesstracker.fitnesstracker.adapter.out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
			
			List<String> lines = FileUtils.readTextFromFile(weightsFile);
			List<WeightData> lastWeekData = weightAnalyzer.extractLastWeeksDataSortedAsc(lines);
			
			boolean isSameWeek = isSameWeek(getLastEntry(lastWeekData), data);
			
			if(data.getDayOfWeek() == DayOfWeek.SUNDAY && isSameWeek) {
				
				writeDataToFile(writer, data, lines);
				drawLineAndAvgWeight(writer, lastWeekData);
				
			}else if(!isSameWeek) {
				
				drawLineAndAvgWeight(writer, lastWeekData);
				printDateAtTheBeginningOfWeek(writer);
				writeDataToFile(writer, data, lines);
				
			}else if(isSameWeek) {
				writeDataToFile(writer, data, lines);
			}

		} catch (Exception e) {
			log.error("Error during persisting weight data to a file.", e);
		}
		
	}

	private boolean isSameWeek(WeightData lastEntry, WeightData dataToPersist) {
		
		// if weekday number of data to persist is greater then last saved entry
		if (dataToPersist.getDayOfWeek().getValue() > lastEntry.getDayOfWeek().getValue()) {
			// difference between those two must be less then 7 days in order to be same week
			if(ChronoUnit.DAYS.between(lastEntry.getTimestamp(), dataToPersist.getTimestamp()) < 7) {
				return true;
			}else {
				return false;
			}
		
		// if weekday number is smaller then last saved entry this can't be a same week
		}else {
			return false;
		}
	}

	private WeightData getLastEntry(List<WeightData> lastWeekData) {
		return lastWeekData.get(lastWeekData.size()-1);
	}

	private void writeDataToFile(Writer writer, WeightData data, List<String> text) {
		try {
			
			if(getLastNotEmptyLine(text).contains(Constants.END_OF_WEEK_LINE)) {
				printDateAtTheBeginningOfWeek(writer);
			}
			
			//TODO: don't add \n at the beginning of the line. If file is empty, this will create first empty line
			writer.append("\n" + ConvertUtils.weekDayToSrbText(data.getTimestamp().getDayOfWeek()));
			writer.append(" ").append(data.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm")));
			writer.append(" " + data.getWeight() + data.getWeightUnit().name());
		} catch (IOException e) {
			log.error("Error writing to a file", e);
		}
	}

	private void printDateAtTheBeginningOfWeek(Writer writer) throws IOException {
		writer.append("\n").append(LocalDateTime.now().format(Constants.DATE_FORMAT));
	}

	private String getLastNotEmptyLine(List<String> list) {
		
		for(int i = list.size() - 1; i >= 0; i--) {
			if(list.get(i).length() > 0) {
				return list.get(i);
			}
		}
		
		return "";
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
