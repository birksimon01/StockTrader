package sbirk.stocks.domain;

import java.math.BigDecimal;

public class Quote {
	
	private String price;
	private String time;
	private boolean closed;
	
	public Quote (String price, String time, boolean closed) {
		this.price = price;
		this.time = time;
		this.closed = closed;
	}
	
	public String getPrice() {
		return price;
	}
	
	public String getTime() {
		return time;
	}
	
	public boolean isClosed() {
		return closed;
	}
}
