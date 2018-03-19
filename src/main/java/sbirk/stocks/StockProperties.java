package sbirk.stocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stocks")
public class StockProperties {
	
	@Min(1)
	@Max(65536)
	private int liveCollectionDelay;
	
	@NotBlank
	private String defaultQsp;
	
	@NotBlank
	private String defaultSaa;
	
	
	private String defaultTp;
	
	
	private List<String> tickers = new ArrayList<String>();
	
	
	
	public List<String> getTickers() {
		return tickers;
	}



	public void setTickers(List<String> tickers) {
		this.tickers = tickers;
	}



	public int getLiveCollectionDelay() {
		return liveCollectionDelay;
	}



	public void setLiveCollectionDelay(int liveCollectionDelay) {
		this.liveCollectionDelay = liveCollectionDelay;
	}



	public String getDefaultQsp() {
		return defaultQsp;
	}



	public void setDefaultQsp(String defaultQsp) {
		this.defaultQsp = defaultQsp;
	}



	public String getDefaultSaa() {
		return defaultSaa;
	}



	public void setDefaultSaa(String defaultSaa) {
		this.defaultSaa = defaultSaa;
	}



	public String getDefaultTp() {
		return defaultTp;
	}



	public void setDefaultTp(String defaultTp) {
		this.defaultTp = defaultTp;
	}
	
	@PostConstruct
	public void print () {
		System.out.println("StockProperties: " + defaultQsp);
		System.out.println("StockProperties: " + defaultSaa);
		System.out.println("StockProperties: " + defaultTp);
	}
}