package sbirk.stocks.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockCore {
	
	private static String dataDir = new String("C:\\Users\\Birk\\Desktop\\StockData\\");
	
	private static HashMap<String, StockQuoteDataCollector> stockQuoteMap;
	
	private List<String> tickerList;
	
	public static void main(String[] args) {
		
	}
	
	public StockCore (List<String> tickers) {
		this.tickerList = new ArrayList<String>();
		if (tickers != null) {
			this.tickerList.addAll(tickers);
		}
		init();
	}
	
	public void init () {
		//adding tickers for test
		tickerList.add("IBM");
		tickerList.add("HD");
		//stuff
		for (String ticker: tickerList) {
			stockQuoteMap.put(ticker, new StockQuoteDataCollector(ticker).start());
			//add in analysis part of program here
		}
	}
	
	public static String getDataDirectory () {
		return dataDir;
	}
	
	public static HashMap<String, StockQuoteDataCollector> getStockQuoteMap() {
		return stockQuoteMap;
	}

	protected static void setStockMap(HashMap<String, StockQuoteDataCollector> stockMap) {
		StockCore.stockQuoteMap = stockMap;
	}
}
