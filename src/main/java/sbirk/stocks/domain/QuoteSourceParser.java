package sbirk.stocks.domain;


public interface QuoteSourceParser {
	
	public String getQuoteSourceName();
	
	public Quote getLiveQuote();
	
	public String getDailyQuoteStatistics();
	
}