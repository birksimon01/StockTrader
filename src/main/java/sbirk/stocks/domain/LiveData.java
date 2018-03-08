package sbirk.stocks.domain;

import java.sql.Timestamp;

public class LiveData {
	
	public LiveData (String ticker, Timestamp time, Double quotePrice) {
		this.ticker = ticker;
		this.time = time;
		this.quotePrice = quotePrice;
		this.closed = false;
	}
	
	private String ticker;
	
	private Timestamp time;
	
	private Double quotePrice;
	
	private boolean closed;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Double getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(Double quotePrice) {
		this.quotePrice = quotePrice;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
}
