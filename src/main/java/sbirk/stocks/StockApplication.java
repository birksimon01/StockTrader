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

@SpringBootApplication
@ComponentScan("sbirk")
@PropertySource("classpath:config.properties")
public class StockApplication {
		
	public static ApplicationContext ctx;
	public static void main (String[] args) {
		ctx = SpringApplication.run(StockApplication.class, args);
		new QSPContextRegistry(ctx);
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
