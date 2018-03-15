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
import sbirk.stocks.domain.TradingPlatform;

@Component
public class TPContextRegistry {

	public String HOME_TP_PACKAGE = "sbirk.stocks.executive";
	
	public HashMap<String, TradingPlatform> tpMap;
	
	@Autowired
	public ApplicationContext ctx;
	
	public TPContextRegistry (ApplicationContext ctx) {
		print("---TP CONTEXT REGISTRY---");
		tpMap = new HashMap<String, TradingPlatform> ();
		Reflections tpReflections = this.getReflections(HOME_TP_PACKAGE);
		Set<Class<? extends QuoteSourceParser>> classes = tpReflections.getSubTypesOf(QuoteSourceParser.class);
		for (Class tp: classes) {
			String name = tp.getName().substring(23);
			TradingPlatform tradingPlatform = (TradingPlatform) ctx.getBean(name);
			tpMap.put(tradingPlatform.getTradingPlatformName(), tradingPlatform);
			print(tradingPlatform.getTradingPlatformName() + " was added to TPContextRegistry");
		}
		print("---TP REGISTRATION COMPLETE---");
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
		System.out.println("TPContextRegistry: " + message);
	}
	
	public TradingPlatform getTP (String name) {
		if (name == null || name == "") return null;
		return tpMap.get(name);
	}
	
	public HashMap<String, TradingPlatform> getTPMap () {
		return tpMap;
	}
}
