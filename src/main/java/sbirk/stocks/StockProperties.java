package sbirk.stocks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class StockProperties {
	
	@Value("${stocks.defaults.qsp}")
	private String defaultQuoteSiteParser;
	
	@Value("${stocks.defaults.tp}")
	private String defaultTradingPlatform;
	
	@Value("${stocks.defualts.tp}")
	private String defaultStockAnalysisAlgorithm;

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