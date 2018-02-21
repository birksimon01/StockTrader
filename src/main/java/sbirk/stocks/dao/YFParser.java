package sbirk.stocks.dao;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.Quote;
import sbirk.stocks.domain.QuoteSourceParser;

@Component
@PropertySource("classpath:config.properties")
public class YFParser implements QuoteSourceParser{
	// QUOTE_SOURCE_NAME = "YahooFinance";
	// quoteSite = "https://finance.yahoo.com/quote/";
	// statsAddon = "/key-statistics?p=";
	
	private final String QUOTE_SOURCE_NAME_PROPERTY = "${yf.sourcename}";
	private final String QUOTE_SITE_PROPERTY = "${yf.source}";
	private final String QUOTE_STATS_ADDON_PROPERTY = "${yf.statsquery}";
	
	protected String ticker = "IBM";
	
	protected Connection statsConnection;
	protected Connection quoteConnection;
	
	@Value (QUOTE_SOURCE_NAME_PROPERTY)
	private String QUOTE_SOURCE_NAME;
	
	@Value (QUOTE_SITE_PROPERTY)
	protected String quoteSite;
	
	@Value (QUOTE_STATS_ADDON_PROPERTY)
	protected String statsAddon;
	
	public YFParser (String ticker) {
		this.ticker=  ticker;
		quoteConnection = Jsoup.connect(quoteSite + ticker);
		statsConnection = Jsoup.connect(quoteSite + ticker + statsAddon + ticker);
	}
	
	public String getQuoteSourceName() {
		return QUOTE_SOURCE_NAME;
	}
	public Quote getLiveQuote () {
		Document doc = null;
		int attempts = 0;
		do {
			attempts++;
			try {
				doc = quoteConnection.get();
			} catch (IOException e) {
				System.out.println("Failed getting doc from quoteConnection. Retrying");
			}
		} while ((doc == null) || attempts <= 3);
		
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
