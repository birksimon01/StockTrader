package sbirk.stocks;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import sbirk.stocks.dao.DailyDataManager;
import sbirk.stocks.registry.QSPContextRegistry;
import sbirk.stocks.registry.SAAContextRegistry;
import sbirk.stocks.registry.TPContextRegistry;

@SpringBootApplication
public class StockApplication {
	
	@Autowired
	public ApplicationContext ctx;
	
	@Autowired
	public QSPContextRegistry qspContextRegistry;
	
	@Autowired
	public TPContextRegistry tpContextRegistry;
	
	@Autowired
	public SAAContextRegistry saaContextRegistry;

	@Autowired
	public DailyDataManager dailyDataManager;
	
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
