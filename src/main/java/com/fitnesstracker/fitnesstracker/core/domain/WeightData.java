package com.fitnesstracker.fitnesstracker.core.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WeightData {

	private LocalDateTime timestamp;
	private Double weight;
	private WeightUnit weightUnit;
	
}
