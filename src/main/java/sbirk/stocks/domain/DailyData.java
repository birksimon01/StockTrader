package sbirk.stocks.domain;

import java.sql.Timestamp;

public class DailyData {
	
	public DailyData (String ticker, Timestamp time, Double dma50, Double dma200, Integer buyVolumes, Double closePrevious, Double openCurrent) {
		this.ticker = ticker;
		this.time = time;
		this.dma50 = dma50;
		this.dma200 = dma200;
		this.buyVolumes = buyVolumes;
		this.closePrevious = closePrevious;
		this.openCurrent = openCurrent;
	}
	
	private String ticker;
	
	private Timestamp time;
	
	private Double dma50;
	
	private Double dma200;
	
	private Integer buyVolumes;
	
	private Double closePrevious;
	
	private Double openCurrent;
	
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public void setDma50(Double dma50) {
		this.dma50 = dma50;
	}

	public void setDma200(Double dma200) {
		this.dma200 = dma200;
	}

	public void setBuyVolumes(Integer buyVolumes) {
		this.buyVolumes = buyVolumes;
	}

	public void setClosePrevious(Double closePrevious) {
		this.closePrevious = closePrevious;
	}

	public void setOpenCurrent(Double openCurrent) {
		this.openCurrent = openCurrent;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public Double getDma50() {
		return dma50;
	}
	
	public Double getDma200() {
		return dma200;
	}

	public Integer getBuyVolumes() {
		return buyVolumes;
	}
	
	public Double getClosePrevious() {
		return closePrevious;
	}

	public Double getOpenCurrent() {
		return openCurrent;
	}
	
	
}
