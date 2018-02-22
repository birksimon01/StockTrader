package sbirk.stocks.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

import sbirk.stocks.domain.StockAnalysisAlgorithm;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Component
public class SimpleMovingAverageAlgorithm implements StockAnalysisAlgorithm {
	public BigDecimal dma50;
	public BigDecimal dma200;
	public String ticker;
	public SimpleMovingAverageAlgorithm () {
		
	}
	public void start(String ticker) {
		this.ticker = ticker;
	}
	@Override
	public String getAlgorithmName() {
		// TODO Auto-generated method stub
		return "DSMA50V200";
	}
	public void start () {
		new Timer().schedule(new MovingAverageAlgorithm(), getStartTime().getTimeInMillis(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}
	
	private class MovingAverageAlgorithm extends TimerTask {
		private int buySellStatus = 0;
		private boolean buy = false;
		private boolean sell = false;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (buySellStatus == 0) {
				if (dma50.compareTo(dma200) == 1) {
					buySellStatus = 1;
				} else if (dma50.compareTo(dma200) == -1) {
					buySellStatus = 2;
				} else {
					//status carries over
				}
			} else {
				if (dma50.compareTo(dma200) == 1) {
					if (buySellStatus == 2) {
						//implement determination of quantity
						buy = true;
					}
					buySellStatus = 1;
				} else if (dma50.compareTo(dma200) == -1) {
					if (buySellStatus == 1) {
						//implement 
						sell = true;
					}
					buySellStatus = 2;
				} else {
					//status carries over
				}
			}
		}
		
	}
	
	public BigDecimal get200DSMA() {
		//Need to find a way to determine date
		return null;
	}
	
	public BigDecimal get50DSMA() {
		// Need a way to compare dates
		// Select 50dsma from daily where time is most recent pretty much
		
		// get Long value, cast to BigDecimal, return
		return null;
	}
	
	public GregorianCalendar getStartTime () {
		return null;
	}
}