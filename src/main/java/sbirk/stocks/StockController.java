package sbirk.stocks;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class StockController {
	
	@RequestMapping("/")
	public String index() {
		return "Stock Trader Program v2";
	}
}
