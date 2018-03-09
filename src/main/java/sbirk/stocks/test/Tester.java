package sbirk.stocks.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sbirk.stocks.core.StockQuoteDataCollector;

public class Tester {
	private static Connection connection = Jsoup.connect("https://finance.yahoo.com/quote/IBM");
	private static String URLboye = new String("https://finance.yahoo.com/quote/ibm");
	public static void main(String[] args) throws InterruptedException {
		StockQuoteDataCollector sQuote = new StockQuoteDataCollector("IBM").start();
	}
	
	public static void dailyTest () {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://finance.yahoo.com/quote/IBM/key-statistics?p=IBM").get();
			Element quoteStats = doc.getElementById("Col1-0-KeyStatistics-Proxy");
			Elements stockStats = quoteStats.getElementsByTag("tr");
			for (Element e: stockStats) {
				System.out.println(e.child(0).text());
				System.out.println(e.child(1).text());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		/*
		Element quoteSummary = doc.getElementById("quote-summary");
		Elements stockInfo = quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) ");
		stockInfo.add(quoteSummary.getElementsByClass("Bxz(bb) Bdbw(1px) Bdbs(s) Bdc($c-fuji-grey-c) H(36px) Bdbw(0)! ").first());
		for(Element info: stockInfo) {
			System.out.println(info.toString());
			Elements cutInfo = info.getElementsByClass("Ta(end) Fw(b) Lh(14px)");
			System.out.println("Data Type: " + cutInfo.first().attr("data-test"));
			String sInfo = cutInfo.first().toString();
			String sValue;
			if (sInfo.contains("-->")) {
				sValue = sInfo.substring(sInfo.indexOf("-->") + 3, sInfo.lastIndexOf("<!--")).trim();
			} else {
				sValue = cutInfo.first().text();
			}
			System.out.println("Value: " + sValue);
			System.out.println("");
		}
		*/
	}
	
	public static void test () {
		long start1 = System.nanoTime();
		Document doc = null;
		try {
			doc = connection.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		long start2 = System.nanoTime();
		String quoteCurrent = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)").toString();
		long start3 = System.nanoTime();
		String quoteFinal = quoteCurrent.substring(quoteCurrent.indexOf("-->") + 3, quoteCurrent.lastIndexOf("<!--")).trim();
		long start4 = System.nanoTime();
		String time = doc.getElementsByClass("C($c-fuji-grey-j) D(b) Fz(12px) Fw(n)").toString();
		long start5 = System.nanoTime();
		System.out.println(quoteFinal);
		int pmIndex = time.indexOf("PM");
		String timeFinal = time.substring(pmIndex-5, pmIndex).trim();
		System.out.println(timeFinal);
		System.out.println("1: " + (start2-start1));
		System.out.println("2: " + (start3-start2));
		System.out.println("3: " + (start4-start3));
		System.out.println("4: " + (start5-start4));
		System.out.println("all: " + (start5-start1));
	}
	public static void getTitle2 () {
		URLConnection con = null;
		try {
			con = new URL(URLboye).openConnection();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
		Matcher m = p.matcher(con.getContentType());
		String charset = m.matches() ? m.group(1) : "ISO-8859-1";
		Reader r;
		try {
			r = new InputStreamReader(con.getInputStream(), charset);
			BufferedReader br = new BufferedReader(r);
			StringBuilder buf = new StringBuilder();
			String line;
			while((line = br.readLine()) != null) {
				buf.append(line);
				System.out.println(line);
				if (line.contains("<span prefix")) {
					System.out.println(line);
					break;
				}
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
