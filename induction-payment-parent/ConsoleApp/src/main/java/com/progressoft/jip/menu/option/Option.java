package com.progressoft.jip.menu.option;

import com.progressoft.jip.menu.action.Action;

public interface Option {
	String getDescription();
	Action getAction();
	void displayOption();
}
