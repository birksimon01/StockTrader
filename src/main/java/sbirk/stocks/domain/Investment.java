package sbirk.stocks.domain;

import java.math.BigDecimal;

public class Investment {
	
	private String ticker;
	
	private BigDecimal boughtAt;
	private int quantity;
	private BigDecimal shortTermFees;
	private BigDecimal shortTermFeesPerShare;
	private BigDecimal longTermFees;
	private BigDecimal longTermFeesPerShare;
	private BigDecimal minProfitablePrice;
	private BigDecimal dividendPayout;
	private BigDecimal basicLongTermProjection;
	
	public Investment (BigDecimal boughtAt, int quantity) {
		this.boughtAt = boughtAt;
		this.quantity = quantity;
		
	}
	
}
