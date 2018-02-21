package sbirk.stocks.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import sbirk.stocks.registry.QSPContextRegistry;

@Component
@PropertySource("classpath:config.properties")
public class QuoteSourceParserFactory {
	
	@Autowired
	protected QSPContextRegistry qspContextRegistry;
	
	@Value ("stocks.defaults.qsp")
	public String qspDefaultName;
	
	public QuoteSourceParser getQSP () {
		return qspContextRegistry.getQSP(qspDefaultName);
	}
	
}
