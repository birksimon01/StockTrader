package sbirk.stocks.registry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.QuoteSourceParser;
import sbirk.stocks.domain.StockAnalysisAlgorithm;

@Component
public class SAAContextRegistry {

	public String HOME_SAA_PACKAGE = "sbirk.stocks.executive";
	
	public HashMap<String, StockAnalysisAlgorithm> saaMap;
	
	@Autowired
	public ApplicationContext ctx;
	
	public SAAContextRegistry (ApplicationContext ctx) {
		print("---SAA CONTEXT REGISTRY---");
		saaMap = new HashMap<String, StockAnalysisAlgorithm> ();
		Reflections saaReflections = this.getReflections(HOME_SAA_PACKAGE);
		Set<Class<? extends StockAnalysisAlgorithm>> classes = saaReflections.getSubTypesOf(StockAnalysisAlgorithm.class);
		for (Class saa: classes) {
			print("SAA name: " + saa.getName().substring(23));
			String name = saa.getName().substring(23);
			if (name.equals("SimpleMovingAverageAlgorithm")) name = new String("simpleMovingAverageAlgorithm");
			StockAnalysisAlgorithm stockAnalysisAlgorithm = (StockAnalysisAlgorithm) ctx.getBean(name);
			saaMap.put(stockAnalysisAlgorithm.getAlgorithmName(), stockAnalysisAlgorithm);
			print(stockAnalysisAlgorithm.getAlgorithmName() + " was added to SAAContextRegistry");
		}
		print("---SAA REGISTRATION COMPLETE---");
	}
	
	private Reflections getReflections(String pkg) {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pkg))));
		return reflections;
	}
	
	private void print (String message) {
		System.out.println("SAAContextRegistry: " + message);
	}
	
	public StockAnalysisAlgorithm getSAA (String name) {
		return saaMap.get(name);
	}
	
	public HashMap<String, StockAnalysisAlgorithm> getSAAMap () {
		return saaMap;
	}
}
