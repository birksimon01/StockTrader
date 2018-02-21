package sbirk.stocks.test;

import sbirk.stocks.core.StockQuote;

public class QSPTest {
	
	public static void main(String[] args) {
		StockQuote sq = new StockQuote("IBM").start();
	}
}
