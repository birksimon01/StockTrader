package sbirk.stocks.domain;

import java.math.BigDecimal;

public class Investment {
	
	private String ticker;
	
	private BigDecimal boughtAt;
	private int quantity;
	
	public Investment (String ticker, BigDecimal boughtAt, int quantity) {
		this.ticker = ticker;
		this.boughtAt = boughtAt;
		this.quantity = quantity;
		
	}
	
}
