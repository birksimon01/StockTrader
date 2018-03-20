package sbirk.stocks.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.dao.DailyDataManager;
import sbirk.stocks.dao.LiveDataManager;
import sbirk.stocks.domain.QuoteSourceParser;
import sbirk.stocks.utils.QuoteSourceParserFactory;

import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn(value = {"QuoteSourceParserFactory", "DailyDataManager", "LiveDataManager"})
public class StockQuoteDataCollector {

	private StockProperties stockProperties = new StockProperties();
	
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
		List<String> tickers = stockProperties.getTickers();
		for (String ticker: tickers) {
			dataCollectorList.add(new DataCollector(ticker, quoteSourceParser, dailyDataManager, liveDataManager, stockProperties.getLiveCollectionDelay()).collect());
		}
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
