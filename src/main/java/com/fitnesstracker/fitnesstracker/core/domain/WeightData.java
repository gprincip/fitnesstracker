package com.fitnesstracker.fitnesstracker.core.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WeightData implements Comparable<WeightData>{

	private LocalDateTime timestamp;
	private Double weight;
	private WeightUnit weightUnit;
	private DayOfWeek dayOfWeek;
	
	@Override
	public int compareTo(WeightData obj) {
		return (timestamp.compareTo(obj.getTimestamp()));
	}
	
}
