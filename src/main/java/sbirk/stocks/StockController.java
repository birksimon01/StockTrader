package sbirk.stocks;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class StockController {
	
	@RequestMapping("/")
	public String index () {
		return "Stock Trader Program v2";
	}
	
	@RequestMapping("/reload")
	public boolean reload () {
		//a link being sent to email after a crash to reload the program's core components.
		//what's returned is the status of whether the reload worked possibly
		return false;
	}
}
