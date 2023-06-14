package com.fitnesstracker.fitnesstracker.adapter.out;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fitnesstracker.fitnesstracker.core.domain.Constants;
import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.util.ConvertUtils;
import com.fitnesstracker.fitnesstracker.util.FileUtils;

public class WeightAnalyzerStandard implements WeightAnalyzerPort{

	@Override
	public List<WeightData> extractLastWeeksData(File weightsFile) {
		
		List<String> lines = FileUtils.readTextFromFile(weightsFile);
		List<WeightData> currentWeek = new ArrayList<>();
		
		for(int i = lines.size() - 1; i > 0; i--) {
			
			if(lines.get(i).contains(Constants.END_OF_WEEK_LINE)) {
				break;
			}
			
			if(lines.get(i).length() == 0) {
				continue;
			}
			
			WeightData data = ConvertUtils.oneLineToWeightData(lines.get(i));
			if(data != null) {
				currentWeek.add(data);
			}
		}
		
		return currentWeek;
	}

	
	
}
