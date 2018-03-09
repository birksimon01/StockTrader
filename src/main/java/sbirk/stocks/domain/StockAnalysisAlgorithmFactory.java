package sbirk.stocks.domain;

import org.springframework.beans.factory.annotation.Autowired;

import sbirk.stocks.StockProperties;
import sbirk.stocks.registry.SAAContextRegistry;

public class StockAnalysisAlgorithmFactory {

	@Autowired
	private SAAContextRegistry saaContextRegistry;
	
	@Autowired
	private StockProperties stockProperties;
	
	public StockAnalysisAlgorithm getSAA () {
		return saaContextRegistry.getSAA(stockProperties.getDefaultStockAnalysisAlgorithm());
	}
}
