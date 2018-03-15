package sbirk.stocks.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.dao.DailyDataManager;
import sbirk.stocks.dao.LiveDataManager;
import sbirk.stocks.domain.QuoteSourceParser;
import sbirk.stocks.domain.QuoteSourceParserFactory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

@Component
public class StockQuoteDataCollector {

	@Autowired
	private StockProperties stockProperties;
	
	@Autowired
	private DailyDataManager dailyDataManager;

	@Autowired
	private LiveDataManager liveDataManager;
	
	private QuoteSourceParserFactory qspFactory;
	
	private QuoteSourceParser quoteSourceParser;
	
	private List<DataCollector> dataCollectorList;
	
	public StockQuoteDataCollector () {
		qspFactory = new QuoteSourceParserFactory();
		dataCollectorList = new ArrayList<DataCollector>();
		System.out.println(qspFactory == null);
		quoteSourceParser = qspFactory.getQSP();
	}
	
	@PostConstruct
	public void test () {
		System.out.println("Collection Test");
		collectData("IBM");
	}
	
	public void collectData (String ticker) {
		dataCollectorList.add(new DataCollector(ticker, quoteSourceParser, dailyDataManager, liveDataManager, stockProperties.getLiveCollectionDelay()).collect());
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
