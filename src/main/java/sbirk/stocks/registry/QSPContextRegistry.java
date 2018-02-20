package sbirk.stocks.registry;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.QuoteSourceParser;

@Component
public class QSPContextRegistry {
	
	public HashMap<String, QuoteSourceParser> qspMap;

	@Autowired
	public ApplicationContext ctx;
	
	public QSPContextRegistry (ApplicationContext ctx) {
		System.out.println("---QSP CONTEXT REGISTRY---");
		qspMap = new HashMap<String, QuoteSourceParser> ();
		for (String beanNames: ctx.getBeanDefinitionNames()) {
			System.out.println("Bean name: " + beanNames);
			
			if (beanNames.equals("TPContextRegistry") 
					|| beanNames.equals("QSPContextRegistry") 
						|| beanNames.equals("SAAContextRegistry")) continue;
			
			Object beanObject = ctx.getBean(beanNames);
			System.out.println("Class: " + beanObject.toString());
			if (beanObject instanceof QuoteSourceParser) {
				QuoteSourceParser qsp = (QuoteSourceParser) beanObject;
				System.out.println(qsp.getQuoteSourceName() + " was added to qspMap");
				qspMap.put(qsp.getQuoteSourceName(), qsp);
			}
		}
		System.out.println("---QSP REGISTRATION COMPLETE---");
	}
	
	public QuoteSourceParser getQSP (String name) {
		return qspMap.get(name);
	}
	
	public HashMap<String, QuoteSourceParser> getQSPMap() {
		return qspMap;
	}
}
