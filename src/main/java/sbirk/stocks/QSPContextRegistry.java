package sbirk.stocks;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;

import sbirk.stocks.domain.QuoteSourceParser;

public class QSPContextRegistry {
	
	public HashMap<String, QuoteSourceParser> qspMap;
	public QSPContextRegistry qspInstance = null;
	
	private QSPContextRegistry (ApplicationContext ctx) {
		for (String beanNames: ctx.getBeanDefinitionNames()) {
			Object beanObject = ctx.getBean(beanNames);
			if (beanObject instanceof QuoteSourceParser) {
				QuoteSourceParser qsp = (QuoteSourceParser) beanObject;
				System.out.println(qsp.getQuoteSourceName() + " was added to qspMap");
				qspMap.put(qsp.getQuoteSourceName(), qsp);
			} else {
				System.out.println(beanObject.toString() + " was not a QuoteSourceParser implementation");
			}
		}
		
	}
	
	public QSPContextRegistry getInstance(ApplicationContext ctx) {
		if (qspInstance == null) {
			qspInstance = new QSPContextRegistry(ctx);
		}
		return qspInstance;
	}
	
	public QuoteSourceParser getQSP (String name) {
		return qspMap.get(name);
	}
}
