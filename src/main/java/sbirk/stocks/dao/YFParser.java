package sbirk.stocks.dao;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import sbirk.stocks.StockProperties;
import sbirk.stocks.domain.Quote;
import sbirk.stocks.domain.QuoteSourceParser;

@Service
public class YFParser implements QuoteSourceParser{
	
	
	private String QUOTE_SOURCE_NAME = "YahooFinance";
	protected String ticker = "IBM";
	protected String quoteSite = "https://finance.yahoo.com/quote/";
	protected String statsAddon = "/key-statistics?p=";
	
	protected Connection statsConnection;
	protected Connection quoteConnection;
	
	public YFParser () {
		quoteConnection = Jsoup.connect(quoteSite + ticker);
		statsConnection = Jsoup.connect(quoteSite + ticker + statsAddon + ticker);
	}
	
	public String getQuoteSourceName() {
		return QUOTE_SOURCE_NAME;
	}
	public Quote getLiveQuote () {
		Document doc = null;
		try {
			doc = quoteConnection.get();
		} catch (IOException e) {
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

	
	public String getDailyQuoteStatistics() {
		return getQuoteSummary() + getStatisticsTable();
	}
	
	private String getQuoteSummary() {
		Document doc = null;
		try {
			doc = quoteConnection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder buf = new StringBuilder();
		Element quoteSummary = doc.getElementById("quote-summary");
		Elements stockInfo = quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) ");
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
		return buf.toString();
	}
	
	private String getStatisticsTable() {
		Document statsDoc = null;
		try {
			statsDoc = statsConnection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder buf = new StringBuilder();
		Element quoteStats = statsDoc.getElementById("Col1-0-KeyStatistics-Proxy");
		Elements stockStats = quoteStats.getElementsByTag("tr");
		for(Element stat: stockStats) {
			buf.append("||" + trimStatName(stat.child(0).text()) + "|" + stat.child(1).text());
		}
		return buf.toString();
	}
	
	private String trimStatName (String valueName) {
		int nameLength = valueName.length();
		if ((valueName.charAt(nameLength - 2) == ' ') && (Character.isDigit(valueName.charAt(nameLength - 1)))) {
			return valueName.substring(0, nameLength - 2);
		}
		return valueName;
	}
}
