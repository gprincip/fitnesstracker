package com.fitnesstracker.fitnesstracker.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fitnesstracker.fitnesstracker.adapter.in.weight.WeightInputAdapter;
import com.fitnesstracker.fitnesstracker.adapter.in.weight.WeightInputAdapterImpl;
import com.fitnesstracker.fitnesstracker.adapter.out.WeightAnalyzerPort;
import com.fitnesstracker.fitnesstracker.adapter.out.WeightAnalyzerStandard;
import com.fitnesstracker.fitnesstracker.adapter.out.WeightPersistAdapter;
import com.fitnesstracker.fitnesstracker.adapter.out.WeightPersistAdapterImpl;

@Configuration
public class BeansConfiguration {

	@Bean
	WeightPersistAdapter weightPersistAdapter() {
		return new WeightPersistAdapterImpl();
	}
	
	@Bean
	WeightInputAdapter weightInputAdapter() {
		return new WeightInputAdapterImpl();
	}
	
	@Bean
	WeightAnalyzerPort weightAnalyzer() {
		return new WeightAnalyzerStandard();
	}
	
}
