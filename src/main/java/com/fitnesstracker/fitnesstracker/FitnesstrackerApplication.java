package com.fitnesstracker.fitnesstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fitnesstracker.fitnesstracker.adapter.in.weight.WeightInputAdapter;
import com.fitnesstracker.fitnesstracker.adapter.in.weight.WeightInputAdapterImpl;
import com.fitnesstracker.fitnesstracker.core.configuration.BeansConfiguration;

@SpringBootApplication
public class FitnesstrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnesstrackerApplication.class, args);
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeansConfiguration.class);
		//WeightInputAdapter weightInputAdapter = new WeightInputAdapterImpl(ctx);
		//weightInputAdapter.takeWeightInputs();
		ctx.getBean(WeightInputAdapter.class).takeWeightInputs();
	}

}
