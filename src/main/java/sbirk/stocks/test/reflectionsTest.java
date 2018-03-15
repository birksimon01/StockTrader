package sbirk.stocks.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import sbirk.stocks.domain.QuoteSourceParser;

public class reflectionsTest {
	
	public static void main (String[] args) {
		reflectionsTest test = new reflectionsTest();
		Reflections daoReflections = test.getReflections("sbirk.stocks.dao");
		Set<Class<? extends QuoteSourceParser>> classes = daoReflections.getSubTypesOf(QuoteSourceParser.class);
		for (Class c: classes) {
			System.out.println(c.getName());
			Class[] interfaces = c.getInterfaces();
			for (Class cl: interfaces) {
				System.out.println("interface: " + cl);
			}
			System.out.println("***");
		}
	}
	
	public Reflections getReflections(String pkg) {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pkg))));
		return reflections;
	}
}
