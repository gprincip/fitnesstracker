package com.fitnesstracker.fitnesstracker.adapter.in.weight;

import java.time.LocalDateTime;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fitnesstracker.fitnesstracker.adapter.out.WeightPersistAdapter;
import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.core.domain.WeightUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeightInputAdapterImpl implements WeightInputAdapter {

	@Autowired
	WeightPersistAdapter weightPeristPort;
	
	public WeightInputAdapterImpl() {}

	@Override
	public void takeWeightInputs() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter today's weight: ");
		
		String weight = scanner.nextLine();
		scanner.close();
		
		LocalDateTime now = LocalDateTime.now();
		
		Double weightDbl = Double.parseDouble(weight);
		
		WeightData data = new WeightData();
		data.setWeight(weightDbl);
		data.setWeightUnit(WeightUnit.KG);
		data.setTimestamp(now);
		data.setDayOfWeek(now.getDayOfWeek());
		
		weightPeristPort.persistWeightData(data);
		
		System.out.println("Thank you!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.error("Error while sleeping!",e);
		}
	}
}
