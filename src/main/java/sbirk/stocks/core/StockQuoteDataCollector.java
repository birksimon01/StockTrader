package sbirk.stocks.core;


import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.dao.DailyDataManager;
import sbirk.stocks.dao.LiveDataManager;
import sbirk.stocks.domain.QuoteSourceParser;
import sbirk.stocks.domain.QuoteSourceParserFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class StockQuoteDataCollector {

	public Date dailyDayLast;
	public String lastDailyStatistic;
	
	protected Connection connection;
	protected Connection statsConnection;
	
	protected String liveFileName = new String("liveQuote.txt");
	protected String dailyFileName = new String("dailyQuote.txt");
	protected String statsAddon = new String("key-statistics?p=");
	
	@Autowired
	private StockProperties stockProperties;
	
	@Autowired
	private DailyDataManager dailyDataManager;

	@Autowired
	private LiveDataManager liveDataManager;
	
	@Autowired
	private QuoteSourceParserFactory qspFactory;
	
	private QuoteSourceParser quoteSourceParser;
	
	private List<DataCollector> dataCollectorList;
	
	public StockQuoteDataCollector () {
		dataCollectorList = new ArrayList<DataCollector>();
		quoteSourceParser = qspFactory.getQSP();
		collectData("IBM");
	}
	
	public void collectData (String ticker) {
		dataCollectorList.add(new DataCollector(ticker, quoteSourceParser, dailyDataManager, liveDataManager, stockProperties.getLiveDataCollectionSecondsDelay()).collect());
	}
	
	public void cancelDataCollection (String ticker) {
		for (DataCollector dataCollector: dataCollectorList) {
			if (dataCollector.getTicker().equals(ticker)) {
				dataCollector.close();
				dataCollectorList.remove(dataCollector);
			}
		}
	}
}
