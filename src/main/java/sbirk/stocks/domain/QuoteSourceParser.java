package sbirk.stocks.domain;


public interface QuoteSourceParser {
	
	public String getQuoteSourceName();
	
	public LiveData getLiveQuote();
	
	public DailyData getDailyQuoteStatistics();
	
	public QuoteSourceParser initialize(String ticker);
}