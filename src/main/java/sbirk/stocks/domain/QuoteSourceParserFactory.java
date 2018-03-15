package sbirk.stocks.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import sbirk.stocks.StockProperties;
import sbirk.stocks.registry.QSPContextRegistry;

@Service
@Configurable
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
