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

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public BigDecimal getBoughtAt() {
		return boughtAt;
	}

	public void setBoughtAt(BigDecimal boughtAt) {
		this.boughtAt = boughtAt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
