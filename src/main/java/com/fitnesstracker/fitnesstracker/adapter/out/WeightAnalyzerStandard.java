package com.fitnesstracker.fitnesstracker.adapter.out;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fitnesstracker.fitnesstracker.core.domain.Constants;
import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.util.ConvertUtils;
import com.fitnesstracker.fitnesstracker.util.FileUtils;

public class WeightAnalyzerStandard implements WeightAnalyzerPort{

	@Override
	public List<WeightData> extractLastWeeksData(File weightsFile) {
		
		List<String> lines = FileUtils.readTextFromFile(weightsFile);
		return extractLastWeeksDataSortedAsc(lines);
	}

	@Override
	public List<WeightData> extractLastWeeksDataSortedAsc(List<String> weightsFileLines) {
		
		List<WeightData> currentWeek = new ArrayList<>();
		LocalDate date = null;
		
		for(int i = weightsFileLines.size() - 1; i > 0; i--) {
			
			if(weightsFileLines.get(i).contains(Constants.END_OF_WEEK_LINE)) {
				break;
			}
			
			if(weightsFileLines.get(i).length() == 0) {
				continue;
			}
			
			date = tryToParseDate(weightsFileLines.get(i));
			
			WeightData data = ConvertUtils.oneLineToWeightData(weightsFileLines.get(i));
			if(data != null) {
				currentWeek.add(data);
			}
		}
		
		populateDatesForFoundEntries(date.atStartOfDay(), currentWeek);
		Collections.sort(currentWeek);
		
		return currentWeek;
	}

	private void populateDatesForFoundEntries(LocalDateTime date, List<WeightData> lastWeek) {
		
		int firstDayAddition = 0 - lastWeek.get(lastWeek.size()-1).getDayOfWeek().getValue();
		
		for(int i = 0; i < lastWeek.size(); i++) {

			WeightData current = lastWeek.get(i);
			current.setTimestamp(date.plus(firstDayAddition + current.getDayOfWeek().getValue(), ChronoUnit.DAYS));
		
		}
		
	}

	private LocalDate tryToParseDate(String line) {
		
		try {
			LocalDate date = LocalDate.parse(line, Constants.DATE_FORMAT);
			if(date != null) {
				return date;
			}
		}catch(Exception e) {
			//ignore
		}
		
		return null;
	}

	
	
}
