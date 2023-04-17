package com.fitnesstracker.fitnesstracker.adapter.in.weight;

import java.time.LocalDateTime;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;

import com.fitnesstracker.fitnesstracker.adapter.out.WeightPersistAdapter;
import com.fitnesstracker.fitnesstracker.core.domain.WeightData;
import com.fitnesstracker.fitnesstracker.core.domain.WeightUnit;

public class WeightInputAdapterImpl implements WeightInputAdapter {

	ApplicationContext context;
	WeightPersistAdapter weightPeristPort;
	
	public WeightInputAdapterImpl(ApplicationContext ctx) {
		this.context = ctx;
		weightPeristPort = ctx.getBean(WeightPersistAdapter.class);
	}

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
		
		weightPeristPort.persistWeightData(data);
		
	}
}
