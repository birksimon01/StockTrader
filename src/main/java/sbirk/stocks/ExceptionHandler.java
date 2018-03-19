package sbirk.stocks;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import sbirk.stocks.tools.EmailSender;

@Component
@DependsOn("EmailSender")
public class ExceptionHandler implements UncaughtExceptionHandler{
	
	@Autowired
	public EmailSender emailSender;
	
	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		emailSender.sendError("An uncaught exception has been thrown. Stack:\r\n" + getStackTrace(arg1));
	}
	
	private String getStackTrace(Throwable throwable) {
	    final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    throwable.printStackTrace(printWriter);
	    return result.toString();
	 }
}
