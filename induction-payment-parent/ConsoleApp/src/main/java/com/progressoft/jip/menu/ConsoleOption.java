package com.progressoft.jip.menu;

import com.progressoft.jip.console.controller.Logging;
import com.progressoft.jip.menu.action.Action;
import com.progressoft.jip.menu.option.Option;

public class ConsoleOption implements Option {
	private Logging logger = Logging.getLoggerInstance(getClass());
	private String description;
	private Integer mappedNumber;
	private Action action;

	public ConsoleOption(String description, Integer mappedNumber, Action action) {
		this.description = description;
		this.mappedNumber = mappedNumber;
		this.action = action;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Action getAction() {
		return this.action;
	}

	@Override
	public void displayOption() {
		logger.println(mappedNumber + " " + description);
	}

	public boolean isMappedNumberEqualTo(Integer mappedNumber) {
		return this.mappedNumber == mappedNumber;
	}

}
