package sbirk.stocks.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import sbirk.stocks.retrieval.StockQuote;

public class StockCore {
	
	protected static HashMap<String, StockQuote> stockMap;
	
	private List<String> tickerList;
	
	private boolean BACKTESTING_MODE = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public StockCore (List<String> tickers) {
		this.tickerList = new ArrayList<String>();
		if (tickers != null) {
			this.tickerList.addAll(tickers);
		}
	}
	
	public void init () {
		//adding tickers for test
		tickerList.add("IBM");
		tickerList.add("HD");
		//stuff
		for (String ticker: tickerList) {
			stockMap.put(ticker, new StockQuote(ticker));
		}
	}
}
