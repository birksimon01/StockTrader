package sbirk.stocks.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.domain.QuoteSourceParser;
import sbirk.stocks.registry.QSPContextRegistry;

@Component
@DependsOn(value = {"QSPContextRegistry", "StockProperties"})
public class QuoteSourceParserFactory {
	
	@Autowired
	private QSPContextRegistry qspContextRegistry;
	
	@Autowired
	private StockProperties stockProperties;
	
	public QuoteSourceParser getQSP () {
		System.out.print("QuoteSourceParserFactory: default qsp -- " + stockProperties.getDefaultQsp());
		return qspContextRegistry.getQSP(stockProperties.getDefaultQsp());
	}
	
}
