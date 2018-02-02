package sbirk.stocks.core;


import java.io.IOException;
import java.io.UncheckedIOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sbirk.stocks.dao.DailyFileManager;
import sbirk.stocks.dao.LiveFileManager;
import sbirk.stocks.dao.YFParser;
import sbirk.stocks.domain.Quote;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class StockQuote {

	public Date dailyDayLast;
	public String lastDailyStatistic;
	
	protected CalendarHelper calendarHelper;
	
	protected Connection connection;
	protected Connection statsConnection;
	
	protected String liveFileName = new String("liveQuote.txt");
	protected String dailyFileName = new String("dailyQuote.txt");
	protected String statsAddon = new String("key-statistics?p=");
	
	private YFParser yahooFinanceParser;
	
	private DailyFileManager dailyFileManager;

	private LiveFileManager liveFileManager;
	
	public StockQuote (String ticker) {
		String quoteSite = new String ("https://finance.yahoo.com/quote/" + ticker);
		connection = Jsoup.connect(quoteSite);
		statsConnection = Jsoup.connect(quoteSite + "/" + statsAddon + ticker);
		calendarHelper = new CalendarHelper();
		
		dailyFileManager = new DailyFileManager(ticker);
		liveFileManager = new LiveFileManager(ticker);
		
		yahooFinanceParser = new YFParser(ticker);
	}
	
	public StockQuote start () {
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
			Element quoteSummary = doc.getElementById("quote-summary");
			Element quoteStats = statsDoc.getElementById("Col1-0-KeyStatistics-Proxy");
			Elements stockInfo = quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) ");
			Elements stockStats = quoteStats.getElementsByTag("tr");
			stockInfo.add(quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) Bdbw(0)! ").first());
			for(Element info: stockInfo) {
				Elements cutInfo = info.getElementsByClass("Ta(end) Fw(b) Lh(14px)");
				String dataType = cutInfo.first().attr("data-test");
				String sInfo = cutInfo.first().toString();
				String sValue;
				if (sInfo.contains("-->")) {
					sValue = sInfo.substring(sInfo.indexOf("-->") + 3, sInfo.lastIndexOf("<!--")).trim();
				} else {
					sValue = cutInfo.first().text();
				}
				buf.append("||" + dataType.replaceAll("-value", "") + "|" + sValue);
			}
			for(Element stat: stockStats) {
				buf.append("||" + stat.child(0).text() + "|" + stat.child(1).text());
			}
			lastDailyStatistic = buf.toString();
			System.out.println("Daily: " + buf.toString());
			dailyFileManager.write(buf.toString(), true);
		}
		
	}
	
	private class fetchLiveQuote extends TimerTask {

		@Override
		public void run() {
			
			Quote liveQuote = yahooFinanceParser.getQuotePrice();
			String liveQuoteFinal = new String(calendarHelper.parseDate(Calendar.getInstance().getTime().toString()) + "||" + liveQuote.getTime() + "||" + liveQuote.getPrice());
			System.out.println(liveQuoteFinal);
			liveFileManager.write(liveQuoteFinal, true);
			
		}
		
	}

	public DailyFileManager getDailyFileManager() {
		return dailyFileManager;
	}
	
	public LiveFileManager getLiveFileManager() {
		return liveFileManager;
	}
	
}
