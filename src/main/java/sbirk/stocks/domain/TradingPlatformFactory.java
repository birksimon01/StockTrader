package sbirk.stocks.domain;

import org.springframework.beans.factory.annotation.Autowired;

import sbirk.stocks.StockProperties;
import sbirk.stocks.registry.TPContextRegistry;

public class TradingPlatformFactory {
	
	@Autowired
	private TPContextRegistry tpContextRegistry;
	
	@Autowired
	private StockProperties stockProperties;
	
	public TradingPlatform getTP (String ticker, double pricePerStock, int sharesToBuy) {
		TradingPlatform tpDefault = tpContextRegistry.getTP(stockProperties.getDefaultTradingPlatform());
		double cost = 0.0;
		
		if (tpDefault == null) {
			if (tpContextRegistry.getTPMap().size() > 1) {
				for (TradingPlatform tp: tpContextRegistry.getTPMap().values()) {
					if (cost == 0.0) {
						tpDefault = tp;
						cost = tp.getTradeCost(ticker, pricePerStock, sharesToBuy);
					} else {
						if (tp.getTradeCost(ticker, pricePerStock, sharesToBuy) < cost) {
							tpDefault = tp;
							cost = tp.getTradeCost(ticker, pricePerStock, sharesToBuy);
						}
					}
				}
			} else if (tpContextRegistry.getTPMap().size() == 1) {
				tpDefault = (TradingPlatform)tpContextRegistry.getTPMap().values().toArray()[0];
			} else {
				System.out.println("NO TRADING PLATFORM FOUND");
				System.exit(1);
			}
		}
		return tpDefault;
	}
}
