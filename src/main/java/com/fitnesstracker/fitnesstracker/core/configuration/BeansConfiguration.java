package com.fitnesstracker.fitnesstracker.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fitnesstracker.fitnesstracker.adapter.out.WeightPersistAdapter;
import com.fitnesstracker.fitnesstracker.adapter.out.WeightPersistAdapterImpl;

@Configuration
public class BeansConfiguration {

	@Bean
	WeightPersistAdapter weightPersistAdapter() {
		return new WeightPersistAdapterImpl();
	}
	
}
