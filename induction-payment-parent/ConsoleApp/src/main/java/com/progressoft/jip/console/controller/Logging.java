package com.progressoft.jip.console.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Logging {

	private static Logger logger ;
	

	static {	
		PropertyConfigurator.configure(Logging.class.getClassLoader().getResourceAsStream("config/log4j.properties"));
	}

	private Logging(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}
	
	public static Logging getLoggerInstance(Class<?> clazz){
		
		return new Logging(clazz);
	}
	

	public void println(String message) {
		logger.info(message);
	}

	public void printf(String format, Object... strings) {
		logger.info(String.format(format, strings));
	}

	public void printErr(String message) {
		logger.info(message);
	}
	
	public void logErr(String message,Throwable e){
		logger.error(message,e);
	}

}
