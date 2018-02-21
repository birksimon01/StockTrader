package sbirk.stocks.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
@Table (name = "LIVE")
public class LiveData {
	
	@Id @GeneratedValue
	@Column (name = "ID", nullable = false)
	private Integer Id;
	
	@Column (name = "TICKER", nullable = false)
	private String ticker;
	
	@Column (name = "TIME", nullable = false)
	private Timestamp time;
	
	@Column (name = "QUOTEPRICE", nullable = false)
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
