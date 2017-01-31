package com.progressoft.jip.console.controller;

import com.progressoft.jip.menu.Menu;
import com.progressoft.jip.menu.option.Option;

public interface Controller<T extends Menu<? extends Option>> {
	void registerMenu(T menu,String referenceMappedKey);
	void setCurrentMenu(String referenceMappedKey);
	void displayCurrentMenu();

	
	
	
}
