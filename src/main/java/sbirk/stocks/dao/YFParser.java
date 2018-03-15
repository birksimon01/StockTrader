package sbirk.stocks.dao;

import java.io.IOException;
import java.sql.Timestamp;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import sbirk.stocks.domain.DailyData;
import sbirk.stocks.domain.LiveData;
import sbirk.stocks.domain.QuoteSourceParser;

@Service
@Configurable
public class YFParser implements QuoteSourceParser{
	
	private final String QUOTE_SOURCE_NAME_PROPERTY = "${yf.sourcename}";
	private final String QUOTE_SITE_PROPERTY = "${yf.source}";
	private final String QUOTE_STATS_ADDON_PROPERTY = "${yf.statsquery}";
	
	protected String ticker;
	
	protected Connection statsConnection;
	protected Connection quoteConnection;
	
	@Value (QUOTE_SOURCE_NAME_PROPERTY)
	private String quoteSourceName;
	
	@Value (QUOTE_SITE_PROPERTY)
	protected String quoteSite;
	
	@Value (QUOTE_STATS_ADDON_PROPERTY)
	protected String statsAddon;
	
	public String getQuoteSourceName() {
		
		return quoteSourceName;
		
	}
	
	public void setQuoteSourceName(String quoteSourceName) {
		
		this.quoteSourceName = quoteSourceName;
		
	}
	
	public String getQuoteSite() {
		
		return quoteSite;
		
	}
	
	public void setQuoteSite(String quoteSite) {
		
		this.quoteSite = quoteSite;
		
	}
	
	public String getStatsAddon() {
		
		return statsAddon;
		
	}
	
	public void setStatsAddon(String statsAddon) {
		
		this.statsAddon = statsAddon;
		
	}
	
	public YFParser () {
		
	}
	
	//TODO: review the structure of this class to allow for it to switch stocks its watching
	public QuoteSourceParser initialize(String ticker) {
		this.ticker=ticker;
		quoteConnection = Jsoup.connect(quoteSite + ticker);
		statsConnection = Jsoup.connect(quoteSite + ticker + statsAddon + ticker);
		return this;
	}
	
	public String getStatistic (String line, String valueName) {
		String[] values = line.split("||");
		for (String value: values) {
			if (value.contains(valueName)) {
				return value.split("|")[1];
			}
		}
		return "";
	}
	
	public LiveData getLiveQuote () {
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

		String quoteFinal = quoteCurrent.substring(quoteCurrent.indexOf("-->") + 3, quoteCurrent.lastIndexOf("<!--")).trim();
		
		long timeMillis = System.currentTimeMillis();

		return new LiveData(ticker, new Timestamp(timeMillis), Double.valueOf(quoteFinal));
	}

	
	public DailyData getDailyQuoteStatistics() {
		String statDump = getQuoteSummary() + getStatisticsTable();
		long timeMillis = System.currentTimeMillis();
		DailyData dailyData = new DailyData(
				ticker, 
				new Timestamp(timeMillis), 
				Double.parseDouble(getStatistic(statDump, "50-Day Moving Average")),
				Double.parseDouble(getStatistic(statDump, "200-Day Moving Average")),
				Integer.parseInt(getStatistic(statDump, "Previous Close")),
				Double.parseDouble(getStatistic(statDump, "Open")),
				Double.parseDouble(getStatistic(statDump, "Volume")),
				Double.parseDouble(getStatistic(statDump, "Forward Dividend & Yield").split(" ")[0]),
				Double.parseDouble(getStatistic(statDump, "52 Week High")),
				Double.parseDouble(getStatistic(statDump, "52 Week Low")));
		return dailyData;
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
