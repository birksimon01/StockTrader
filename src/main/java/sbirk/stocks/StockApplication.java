package sbirk.stocks;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import sbirk.stocks.core.StockQuoteDataCollector;
import sbirk.stocks.registry.QSPContextRegistry;
import sbirk.stocks.registry.SAAContextRegistry;
import sbirk.stocks.registry.TPContextRegistry;

@SpringBootApplication
@ComponentScan("sbirk")
public class StockApplication {
	
	@Autowired
	public CommonBeans commonBeans;
	
	@Autowired
	public StockProperties stockProperties;
	
	@Autowired
	public QSPContextRegistry qspContextRegistry;
	
	@Autowired
	public TPContextRegistry tpContextRegistry;
	
	@Autowired
	public SAAContextRegistry saaContextRegistry;
	
	public static void main (String[] args) throws InterruptedException {
		SpringApplication.run(StockApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner (ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName: beanNames) {
				System.out.println(beanName);
			}
		};
	}
}
