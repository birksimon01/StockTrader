package sbirk.stocks.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DAILY")
public class DailyData implements Serializable {
	
	@Id @GeneratedValue
	@Column (name = "ID")
	private Integer Id;
	
	@Column (name = "TICKER")
	private String ticker;
	
	@Column (name = "TIME")
	private Timestamp time;
	
	@Column (name = "DMA50")
	private Long dma50;
	
	@Column (name = "DMA200")
	private Long dma200;
	
	@Column (name = "BUYVOL")
	private Long buyVolumes;
	
	@Column (name = "CLSPREV")
	private Long closePrevious;
	
	@Column (name = "OPENCUR")
	private Long openCurrent;
	
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public void setDma50(Long dma50) {
		this.dma50 = dma50;
	}

	public void setDma200(Long dma200) {
		this.dma200 = dma200;
	}

	public void setBuyVolumes(Long buyVolumes) {
		this.buyVolumes = buyVolumes;
	}

	public void setClosePrevious(Long closePrevious) {
		this.closePrevious = closePrevious;
	}

	public void setOpenCurrent(Long openCurrent) {
		this.openCurrent = openCurrent;
	}
	
	public Integer getId () {
		return Id;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public Long getDma50() {
		return dma50;
	}
	
	public Long getDma200() {
		return dma200;
	}

	public Long getBuyVolumes() {
		return buyVolumes;
	}
	
	public Long getClosePrevious() {
		return closePrevious;
	}

	public Long getOpenCurrent() {
		return openCurrent;
	}
	
	
}
