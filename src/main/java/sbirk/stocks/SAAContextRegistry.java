package sbirk.stocks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.StockAnalysisAlgorithm;

@Component
public class SAAContextRegistry {

	public HashMap<String, StockAnalysisAlgorithm> saaMap;
	
	@Autowired
	public ApplicationContext ctx;
	
	public SAAContextRegistry (ApplicationContext ctx) {
		System.out.println("---SAA CONTEXT REGISTRY---");
		saaMap = new HashMap<String, StockAnalysisAlgorithm> ();
		for (String beanNames: ctx.getBeanDefinitionNames()) {
			System.out.println("Bean name: " + beanNames);
			
			if (beanNames.equals("TPContextRegistry") 
					|| beanNames.equals("QSPContextRegistry") 
						|| beanNames.equals("SAAContextRegistry")) continue;
			
			Object beanObject = ctx.getBean(beanNames);
			System.out.println("Class: " + beanObject.toString());
			if (beanObject instanceof StockAnalysisAlgorithm) {
				StockAnalysisAlgorithm saa = (StockAnalysisAlgorithm) beanObject;
				System.out.println(saa.getAlgorithmName() + " was added to SAA Map");
				saaMap.put(saa.getAlgorithmName(), saa);
			}
		}
		System.out.println("---SAA REGISTRATION COMPLETE---");
	}
	
	public StockAnalysisAlgorithm getSAA (String name) {
		return saaMap.get(name);
	}
	
	public HashMap<String, StockAnalysisAlgorithm> getSAAMap () {
		return saaMap;
	}
}
