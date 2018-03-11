package sbirk.stocks.domain;

public interface TradingPlatform {

	public String getTradingPlatformName();
	
	public double getTradeCost(String ticker, double pricePerStock, int sharesToBuy);
}