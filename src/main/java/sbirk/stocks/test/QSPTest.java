package sbirk.stocks.test;

import sbirk.stocks.core.StockQuoteDataCollector;

public class QSPTest {
	
	public static void main(String[] args) {
		StockQuoteDataCollector sq = new StockQuoteDataCollector("IBM").start();
	}
}
