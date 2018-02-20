package sbirk.stocks.registry;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.TradingPlatform;

@Component
public class TPContextRegistry {

	public HashMap<String, TradingPlatform> tpMap;
	
	@Autowired
	public ApplicationContext ctx;
	
	public TPContextRegistry (ApplicationContext ctx) {
		System.out.println("---TP CONTEXT REGISTRY---");
		tpMap = new HashMap<String, TradingPlatform> ();
		for (String beanNames: ctx.getBeanDefinitionNames()) {
			System.out.println("Bean name: " + beanNames);
			
			if (beanNames.equals("TPContextRegistry") 
					|| beanNames.equals("QSPContextRegistry") 
						|| beanNames.equals("SAAContextRegistry")) continue;
			
			Object beanObject = ctx.getBean(beanNames);
			System.out.println("Class: " + beanObject.toString());
			if (beanObject instanceof TradingPlatform) {
				TradingPlatform tp = (TradingPlatform) beanObject;
				System.out.println(tp.getTradingPlatformName() + " was added to tpMap");
				tpMap.put(tp.getTradingPlatformName(), tp);
			}
		}
		System.out.println("---TP REGISTRATION COMPLETE---");
	}
	
	public TradingPlatform getTP (String name) {
		return tpMap.get(name);
	}
	
	public HashMap<String, TradingPlatform> getTPMap () {
		return tpMap;
	}
}
