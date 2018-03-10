package sbirk.stocks.core;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import sbirk.stocks.dao.DailyDataManager;
import sbirk.stocks.dao.LiveDataManager;
import sbirk.stocks.domain.QuoteSourceParser;

public class DataCollector {
	
	public String ticker;
	
	public QuoteSourceParser quoteSourceParser;
	
	public DailyDataManager dailyDataManager;
	
	public LiveDataManager liveDataManager;
	
	public Timer timer;
	
	public int liveCollectionDelaySeconds;
	
	public DataCollector (String ticker, QuoteSourceParser quoteSourceParser, DailyDataManager dailyDataManager, LiveDataManager liveDataManager, int liveCollectionDelaySeconds) {
		this.ticker = ticker;
		this.quoteSourceParser = quoteSourceParser.initialize(ticker);
		this.dailyDataManager = dailyDataManager;
		this.liveDataManager = liveDataManager;
		this.liveCollectionDelaySeconds = liveCollectionDelaySeconds;
	}
	
	public String getTicker () {
		return ticker;
	}
	
	public DataCollector collect () {
		timer = new Timer();
		timer.schedule(new fetchStockInfo(), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
 		timer.schedule(new fetchLiveQuote(), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(liveCollectionDelaySeconds, TimeUnit.SECONDS));
 		return this;
	}
	
	public void close () {
		timer.cancel();
		//implement a close method to neutralize quoteSourceParser connections
	}
	
	private class fetchStockInfo extends TimerTask {
		
		@Override
		public void run() {
			dailyDataManager.addDailyDataEntry(quoteSourceParser.getDailyQuoteStatistics());
		}
	}
	
	private class fetchLiveQuote extends TimerTask {

		@Override
		public void run() {
			liveDataManager.addLiveDataEntry(quoteSourceParser.getLiveQuote());
		}
	}
	
}
