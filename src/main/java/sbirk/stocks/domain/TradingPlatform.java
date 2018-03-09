package sbirk.stocks.domain;

import org.springframework.context.annotation.Configuration;

@Configuration
public interface TradingPlatform {

	public String getTradingPlatformName();
	
	public double getTradeCost(String ticker, double pricePerStock, int sharesToBuy);
}