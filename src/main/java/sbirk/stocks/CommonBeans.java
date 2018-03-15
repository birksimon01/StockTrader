package sbirk.stocks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sbirk.stocks.executive.SimpleMovingAverageAlgorithm;

@Configuration
public class CommonBeans {
	
	@Bean
	public SimpleMovingAverageAlgorithm simpleMovingAverageAlgorithm () {
		return new SimpleMovingAverageAlgorithm();
	}
}
