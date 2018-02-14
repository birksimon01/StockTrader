package sbirk.stocks;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class StockController {
	
	private static final Logger logger = LoggerFactory.getLogger(StockController.class);
	
	@RequestMapping("/")
	public String index() {
		return "Stock Trader Program v2";
	}
}
