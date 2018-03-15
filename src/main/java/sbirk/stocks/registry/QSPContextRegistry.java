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

@Component
public class QSPContextRegistry {
	
	public String HOME_QSP_PACKAGE = "sbirk.stocks.dao";
	
	public HashMap<String, QuoteSourceParser> qspMap;

	@Autowired
	public ApplicationContext ctx;
	
	public QSPContextRegistry (ApplicationContext ctx) {
		print("---QSP CONTEXT REGISTRY---");
		qspMap = new HashMap<String, QuoteSourceParser> ();
		Reflections qspReflections = this.getReflections(HOME_QSP_PACKAGE);
		Set<Class<? extends QuoteSourceParser>> classes = qspReflections.getSubTypesOf(QuoteSourceParser.class);
		for (Class qsp: classes) {
			print("QSP name: " + qsp.getName().substring(17));
			String name = qsp.getName().substring(17);
			QuoteSourceParser quoteSourceParser = (QuoteSourceParser) ctx.getBean(name);
			System.out.println("QSPContextRegistry: is qsp null -- " + (quoteSourceParser == null));
			qspMap.put(quoteSourceParser.getQuoteSourceName(), quoteSourceParser);
			print(quoteSourceParser.getQuoteSourceName() + " was added to QSPContextRegistry");
		}
		print("---QSP REGISTRATION COMPLETE---");
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
		System.out.println("QSPContextRegistry: " + message);
	}
	
	public QuoteSourceParser getQSP (String name) {
		return qspMap.get(name);
	}
	
	public HashMap<String, QuoteSourceParser> getQSPMap() {
		return qspMap;
	}
}
