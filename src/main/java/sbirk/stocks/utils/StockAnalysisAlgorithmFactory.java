package sbirk.stocks.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.domain.StockAnalysisAlgorithm;
import sbirk.stocks.registry.SAAContextRegistry;

@Component
public class StockAnalysisAlgorithmFactory {

	@Autowired
	private SAAContextRegistry saaContextRegistry;
	
	private StockProperties stockProperties = new StockProperties();
	
	public StockAnalysisAlgorithm getSAA () {
		return saaContextRegistry.getSAA(stockProperties.getDefaultSaa());
	}
}
