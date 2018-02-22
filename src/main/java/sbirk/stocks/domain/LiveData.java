package sbirk.stocks.domain;

import java.sql.Timestamp;

public class LiveData {
	
	private Integer Id;
	
	private String ticker;
	
	private Timestamp time;
	
	private String quotePrice;

	public Integer getId() {
		return Id;
	}
	public String getTicker() {
		return ticker;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public String getQuotePrice() {
		return quotePrice;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public void setQuotePrice(String quotePrice) {
		this.quotePrice = quotePrice;
	}
	
}
