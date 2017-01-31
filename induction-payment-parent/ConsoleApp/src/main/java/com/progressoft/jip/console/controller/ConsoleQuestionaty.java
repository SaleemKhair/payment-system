package com.progressoft.jip.console.controller;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ConsoleQuestionaty {
	private Logging logger = Logging.getLoggerInstance(getClass());
	private Scanner scanner = new Scanner(System.in);
	private Set<String> questions = new LinkedHashSet<>();
	private Map<String,String> questionAnswerMap = new HashMap<>();
	
	public void addQuestion(String question){
		questions.add(question);
	}	
	public void startQuestionary(){
		questions.stream().forEach(q -> {
			logger.println(q);
			String userInput = scanner.nextLine();
			questionAnswerMap.put(q, userInput);
		});
	}
	
	public String getAnswer(String question){
		return this.questionAnswerMap.get(question);
	}
	
}
