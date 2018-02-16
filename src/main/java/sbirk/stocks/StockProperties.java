package sbirk.stocks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "stocks")
public class StockProperties {
	
	private String ticker;
	private String quoteSite;
	private String statsAddon;
	private String stockDataDirectory;
	
	private int secondsBetweenLiveQuoteFetch;
	
	private boolean showLiveQuoteStream;
	private boolean showDailyQuoteStream;
	private boolean showTransactions;
	
	private boolean backtestingMode;

	public boolean isBacktestingMode() {
		return backtestingMode;
	}

	public boolean isShowTransactions() {
		return showTransactions;
	}

	public boolean isShowDailyQuoteStream() {
		return showDailyQuoteStream;
	}
	public boolean isShowLiveQuoteStream() {
		return showLiveQuoteStream;
	}

	public int getSecondsBetweenLiveQuoteFetch() {
		return secondsBetweenLiveQuoteFetch;
	}
	
	public String getTicker() {
		return ticker;
	}
	public String getStockDataDirectory() {
		return stockDataDirectory;
	}

	public String getQuoteStatsResponse() {
		return statsAddon;
	}

	public String getQuoteSite() {
		return quoteSite;
	}
}
