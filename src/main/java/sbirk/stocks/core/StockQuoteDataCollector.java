package sbirk.stocks.core;


import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sbirk.stocks.dao.DailyDataManager;
import sbirk.stocks.dao.LiveDataManager;
import sbirk.stocks.dao.YFParser;
import sbirk.stocks.domain.QuoteSourceParser;
import sbirk.stocks.domain.QuoteSourceParserFactory;
import sbirk.stocks.test.CalendarHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Component
public class StockQuoteDataCollector {

	public Date dailyDayLast;
	public String lastDailyStatistic;
	
	protected CalendarHelper calendarHelper;
	
	protected Connection connection;
	protected Connection statsConnection;
	
	protected String liveFileName = new String("liveQuote.txt");
	protected String dailyFileName = new String("dailyQuote.txt");
	protected String statsAddon = new String("key-statistics?p=");
	
	
	@Autowired
	private DailyDataManager dailyDataManager;

	@Autowired
	private LiveDataManager liveDataManager;
	
	@Autowired
	private QuoteSourceParserFactory qspFactory;
	
	private QuoteSourceParser quoteSourceParser;
	
	public StockQuoteDataCollector (String ticker) {
		quoteSourceParser = qspFactory.getQSP();
	}
	
	public StockQuoteDataCollector start () {
		Timer timer = new Timer();
		timer.schedule(new fetchStockInfo(), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
		timer.schedule(new fetchLiveQuote(), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(3, TimeUnit.SECONDS));
		return this;
	}
	
	private class fetchStockInfo extends TimerTask {

		@Override
		public void run() {
			try {
				Document doc = connection.get();
				Document statsDoc = statsConnection.get();
				getStats(doc, statsDoc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void getStats (Document doc, Document statsDoc) {
			StringBuilder buf = new StringBuilder();
			dailyDayLast = Calendar.getInstance().getTime();
			buf.append(calendarHelper.parseDate(dailyDayLast.toString()));
			buf.append(yahooFinanceParser.getDailyQuoteStatistics());
			lastDailyStatistic = buf.toString();
			System.out.println("Daily: " + buf.toString());
			dailyFileManager.write(buf.toString(), true);
		}
		
	}
	
	private class fetchLiveQuote extends TimerTask {

		@Override
		public void run() {
			
			Quote liveQuote = yahooFinanceParser.getLiveQuote();
			String liveQuoteFinal = new String(calendarHelper.parseDate(Calendar.getInstance().getTime().toString()) + "||" + liveQuote.getTime() + "||" + liveQuote.getPrice());
			System.out.println(liveQuoteFinal);
			liveFileManager.write(liveQuoteFinal, true);
			
		}
		
	}
	
}
