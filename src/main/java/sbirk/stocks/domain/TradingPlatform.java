package sbirk.stocks.domain;

import org.springframework.context.annotation.Configuration;

@Configuration
public interface TradingPlatform {

	public String getTradingPlatformName();
	
}