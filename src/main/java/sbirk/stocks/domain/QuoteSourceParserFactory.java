package sbirk.stocks.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.registry.QSPContextRegistry;

@Component
public class QuoteSourceParserFactory {
	
	@Autowired
	private QSPContextRegistry qspContextRegistry;
	
	@Autowired
	private StockProperties stockProperties;
	
	public QuoteSourceParser getQSP () {
		return qspContextRegistry.getQSP(stockProperties.getDefaultQuoteSiteParser());
	}
	
}
