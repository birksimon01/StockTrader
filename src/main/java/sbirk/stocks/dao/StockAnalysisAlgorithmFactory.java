package sbirk.stocks.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import sbirk.stocks.StockProperties;
import sbirk.stocks.domain.StockAnalysisAlgorithm;
import sbirk.stocks.registry.SAAContextRegistry;

@Component
@DependsOn(value = {"SAAContextRegistry", "StockProperties"})
public class StockAnalysisAlgorithmFactory {

	@Autowired
	private SAAContextRegistry saaContextRegistry;
	
	@Autowired
	private StockProperties stockProperties;
	
	public StockAnalysisAlgorithm getSAA () {
		return saaContextRegistry.getSAA(stockProperties.getDefaultSaa());
	}
}
