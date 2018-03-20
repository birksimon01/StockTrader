package sbirk.stocks.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.domain.QuoteSourceParser;
import sbirk.stocks.registry.QSPContextRegistry;

@Component
public class QuoteSourceParserFactory {
	
	@Autowired
	private QSPContextRegistry qspContextRegistry;
	
	private StockProperties stockProperties = new StockProperties();
	
	public QuoteSourceParser getQSP () {
		System.out.print("QuoteSourceParserFactory: default qsp -- " + stockProperties.getDefaultQsp());
		return qspContextRegistry.getQSP(stockProperties.getDefaultQsp());
	}
	
}
