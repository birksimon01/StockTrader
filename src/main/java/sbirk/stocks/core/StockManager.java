package sbirk.stocks.core;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import sbirk.stocks.retrieval.StockQuote;

public class StockManager {
	
	public boolean BACKTESTING_MODE = false;
	public String ticker;
	
	public StockManager (String ticker) {
		this.ticker = ticker;
	}
	
	public StockManager start () {
		Timer timer = new Timer();
		timer.schedule(new MovingAveragesTask(ticker), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
		return this;
	}
	
	private class MovingAveragesTask extends TimerTask {
		
		private String ticker;
		private StockQuote sQuote;
		private FileRW fileRW;	
		private int buySellStatus;		
		private BigDecimal dma50Previous;
		private BigDecimal dma200Previous;
		private BigDecimal dma50;
		private BigDecimal dma200;
		
		public MovingAveragesTask (String ticker) {
			this.ticker = ticker;
			// 0 = indeterminate
			// 1 = 50dma > 200dma || BUY status
			// 2 = 50dma < 200dma || SELL status
			buySellStatus = 0;
			sQuote = stockMap.get(ticker);
			fileRW = sQuote.getFileRW();
		}
		
		@Override
		public void run() {
			String[] date = calendar.getTime().toString().split(" ");
			String day = new String(date[1] + " " + date[2]);
			String year = date[5];
			dma50 = new BigDecimal(fileRW.getStatistic(day, year, "50-Day Moving Average"));
			dma200 = new BigDecimal(fileRW.getStatistic(day, year, "200-Day Moving Average"));
			// make this more permanent in future*** i.e. store in file or grab from file in constructor
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
						buyOrder(ticker, BigDecimal.ZERO, 0, 0, BACKTESTING_MODE);
					}
					buySellStatus = 1;
				} else if (dma50.compareTo(dma200) == -1) {
					if (buySellStatus == 1) {
						//implement 
						sellOrder(ticker, BigDecimal.ZERO, 0, 0, BACKTESTING_MODE);
					}
					buySellStatus = 2;
				} else {
					//status carries over
				}
			}
		}
	}
}
