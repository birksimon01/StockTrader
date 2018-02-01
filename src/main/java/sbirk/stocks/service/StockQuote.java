package sbirk.stocks.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sbirk.stocks.dao.FileRW;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;

public class StockQuote {
	
	private String symbol;
	
	public Date dailyDayLast;
	
	protected Connection connection;
	protected Connection statsConnection;
	
	protected String liveFilePath = new String("C:\\Users\\Birk\\Desktop\\StockData\\");
	protected String dailyFilePath = new String("C:\\Users\\Birk\\Desktop\\StockData\\");
	protected String liveFileName = new String("liveQuote.txt");
	protected String dailyFileName = new String("dailyQuote.txt");
	protected String statsAddon = new String("key-statistics?p=");
	
	protected FileRW fileWriter;
	
	public StockQuote (String symbol) {
		this.symbol = symbol;
		String quoteSite = new String ("https://finance.yahoo.com/quote/" + symbol);
		connection = Jsoup.connect(quoteSite);
		statsConnection = Jsoup.connect(quoteSite + "/" + statsAddon + symbol);
		new File(liveFilePath + symbol).mkdir();
		Path liveFile = Paths.get(liveFilePath + symbol + "\\" + liveFileName);
		Path dailyFile = Paths.get(dailyFilePath + symbol + "\\" + dailyFileName);
		System.out.println("liveQuote.txt startup initiation");
		try {
		    // Create the empty file with default permissions, etc.
		    Files.createFile(liveFile);
		    System.out.println("liveQuote.txt not found, successfully created");
		} catch (FileAlreadyExistsException x) {
			//Does nothing if file already exists
			System.out.println("liveQuote.txt already created, no file initialization needed");
		} catch (IOException x) {
		    // Some other sort of failure, such as permissions.
			System.out.println("liveQuote.txt file creation failed, program terminating");
			System.exit(1);
		}
		
		System.out.println("dailyQuote.txt startup initiation");
		try {
		    // Create the empty file with default permissions, etc.
		    Files.createFile(dailyFile);
		    System.out.println("dailyQuote.txt not found, successfully created");
		} catch (FileAlreadyExistsException x) {
			//Does nothing if file already exists
			System.out.println("dailyQuote.txt already created, no file initialization needed");
		} catch (IOException x) {
		    // Some other sort of failure, such as permissions.
			System.out.println("dailyQuote.txt file creation failed, program terminating");
			System.exit(1);
		}
		
		fileWriter = new FileRW(symbol);
	}
	
	public FileRW getFileRW(){
		return fileWriter;
	}
	
	public StockQuote start () {
		Timer timer = new Timer();
		timer.schedule(new fetchStockInfo(), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
		timer.schedule(new fetchLiveQuote(), Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS.convert(3, TimeUnit.SECONDS));
		return this;
	}
	
	private class fetchStockInfo extends TimerTask {

		@Override
		public void run() {
			try {
				Document doc = connection.get();
				Document statsDoc = statsConnection.get();
				getStats(doc, statsDoc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void getStats (Document doc, Document statsDoc) {
			StringBuilder buf = new StringBuilder();
			dailyDayLast = Calendar.getInstance().getTime();
			buf.append(dailyDayLast.toString());
			Element quoteSummary = doc.getElementById("quote-summary");
			Element quoteStats = statsDoc.getElementById("Col1-0-KeyStatistics-Proxy");
			Elements stockInfo = quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) ");
			Elements stockStats = quoteStats.getElementsByTag("tr");
			stockInfo.add(quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) Bdbw(0)! ").first());
			for(Element info: stockInfo) {
				Elements cutInfo = info.getElementsByClass("Ta(end) Fw(b) Lh(14px)");
				String dataType = cutInfo.first().attr("data-test");
				String sInfo = cutInfo.first().toString();
				String sValue;
				if (sInfo.contains("-->")) {
					sValue = sInfo.substring(sInfo.indexOf("-->") + 3, sInfo.lastIndexOf("<!--")).trim();
				} else {
					sValue = cutInfo.first().text();
				}
				buf.append("||" + dataType.replaceAll("-value", "") + "|" + sValue);
			}
			for(Element stat: stockStats) {
				buf.append("||" + stat.child(0).text() + "|" + stat.child(1).text());
			}
			System.out.println("Daily: " + buf.toString());
			fileWriter.write(buf.toString() + "\r\n", false);
		}
		
	}
	
	private class fetchLiveQuote extends TimerTask {

		@Override
		public void run() {
			try {
				Document doc = connection.get();
				String quoteCurrent = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)").toString();
				String time = doc.getElementById("quote-market-notice").toString();
				if (time.contains("close")) return;
				String quoteFinal = quoteCurrent.substring(quoteCurrent.indexOf("-->") + 3, quoteCurrent.lastIndexOf("<!--")).trim();
				int timeIndex = time.indexOf("PM");
				if (timeIndex == -1) {
					timeIndex = time.indexOf("AM");
				}
				String timeFinal = time.substring(timeIndex-5, timeIndex).trim();
				String liveQuoteFinal = new String(Calendar.getInstance().getTime() + "||" + timeFinal + "||" + quoteFinal);
				System.out.println(liveQuoteFinal);
				fileWriter.write(liveQuoteFinal + "\r\n", true);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UncheckedIOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
