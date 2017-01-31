package com.progressoft.jip.console.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.progressoft.jip.menu.ConsoleMenu;

public class ConsoleController implements Controller<ConsoleMenu> {
	private Logging logger = Logging.getLoggerInstance(getClass());
	private Scanner scanner = new Scanner(System.in);
	private Map<String, ConsoleMenu> menusMap = new HashMap<>();
	private ConsoleMenu currentMenu;

	@Override
	public void registerMenu(ConsoleMenu menu, String referenceMappedKey) {
		this.menusMap.put(referenceMappedKey, menu);
	}

	@Override
	public void setCurrentMenu(String referenceMappedKey) {
		this.currentMenu = menusMap.get(referenceMappedKey);
	}

	@Override
	public void displayCurrentMenu() {
		logger.println(this.currentMenu.getTitle());
		currentMenu.displayOptions();
		invokeCurrentMenuOption(Integer.parseInt(scanner.nextLine()));
	}

	private void invokeCurrentMenuOption(Integer i) {
		currentMenu.invokeOption(i);
	}

}
