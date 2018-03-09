package sbirk.stocks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class StockProperties {
	
	public StockProperties () {
		System.out.println(this.defaultQuoteSiteParser);
		System.out.println(this.defaultTradingPlatform);
		System.out.println(this.defaultStockAnalysisAlgorithm);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Value("${stocks.collection.livedata.secondsDelayBetweenCollection}")
	private int liveDataCollectionSecondsDelay;
	
	@Value("${stocks.defaults.qsp}")
	private String defaultQuoteSiteParser;
	
	@Value("${stocks.defaults.tp}")
	private String defaultTradingPlatform;
	
	@Value("${stocks.defualts.tp}")
	private String defaultStockAnalysisAlgorithm;

	public int getLiveDataCollectionSecondsDelay() {
		return liveDataCollectionSecondsDelay;
	}

	public String getDefaultQuoteSiteParser() {
		return defaultQuoteSiteParser;
	}

	public String getDefaultTradingPlatform() {
		return defaultTradingPlatform;
	}

	public String getDefaultStockAnalysisAlgorithm() {
		return defaultStockAnalysisAlgorithm;
	}
	
}