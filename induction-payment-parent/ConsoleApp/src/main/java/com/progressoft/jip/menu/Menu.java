package com.progressoft.jip.menu;

import com.progressoft.jip.menu.option.Option;

public interface Menu<T extends Option> {
	String getTitle();
	void displayOptions();
	void addOption(T option);
	void removeOption(T option);
	void invokeOption(Integer optionMappedKey);
	
}
