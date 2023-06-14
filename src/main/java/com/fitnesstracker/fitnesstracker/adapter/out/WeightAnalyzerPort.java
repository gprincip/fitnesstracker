package com.fitnesstracker.fitnesstracker.adapter.out;

import java.io.File;
import java.util.List;

import com.fitnesstracker.fitnesstracker.core.domain.WeightData;

public interface WeightAnalyzerPort {

	List<WeightData> extractLastWeeksData(File weightsFile);

}
