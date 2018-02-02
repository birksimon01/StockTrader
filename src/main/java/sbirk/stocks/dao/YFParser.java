package sbirk.stocks.dao;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sbirk.stocks.domain.Quote;

public class YFParser {
	
	protected String quoteSite = new String ("https://finance.yahoo.com/quote/");
	protected String statsAddon = new String("/key-statistics?p=");
	
	protected Connection statsConnection;
	protected Connection quoteConnection;
	
	public YFParser (String ticker) {
		quoteConnection = Jsoup.connect(quoteSite + ticker);
		statsConnection = Jsoup.connect(quoteSite + ticker + statsAddon + ticker);
	}
	
	public Quote getQuotePrice () {
		Document doc = null;
		try {
			doc = quoteConnection.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String quoteCurrent = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)").toString();
		String time = doc.getElementById("quote-market-notice").toString();
		String quoteFinal = quoteCurrent.substring(quoteCurrent.indexOf("-->") + 3, quoteCurrent.lastIndexOf("<!--")).trim();
		int timeIndex = time.indexOf("PM");
		if (timeIndex == -1) {
			timeIndex = time.indexOf("AM");
		}
		String timeFinal = time.substring(timeIndex-5, timeIndex).trim();
		return new Quote (quoteFinal, timeFinal, time.contains("closed"));
	}
	
	public String getQuoteSummary() {
		Document doc = null;
		try {
			doc = quoteConnection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder buf = new StringBuilder();
		Element quoteSummary = doc.getElementById("quote-summary");
		Elements stockInfo = quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) ");
	}
	
	public String getStatisticsTable() {
		
	}
}
