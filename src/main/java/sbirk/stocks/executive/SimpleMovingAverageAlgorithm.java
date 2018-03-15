package sbirk.stocks.executive;

import org.springframework.stereotype.Component;

import sbirk.stocks.domain.StockAnalysisAlgorithm;

@Component
public class SimpleMovingAverageAlgorithm implements StockAnalysisAlgorithm {
	
	public SimpleMovingAverageAlgorithm () {
		
	}
	
	@Override
	public String getAlgorithmName() {
		// TODO Auto-generated method stub
		return "DSMA50V200";
	}

}
