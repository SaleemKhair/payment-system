package com.progressoft.jip.menu;

import java.util.LinkedHashSet;
import java.util.Set;

public class ConsoleMenu implements Menu<ConsoleOption> {
	private Set<ConsoleOption> menuOptions = new LinkedHashSet<>();
	private String menuTitle;
	private ConsoleOption exitOption = new ConsoleOption("Exit", 0, ()-> System.exit(0));

	public ConsoleMenu(String menuTitle) {
		this.menuTitle = menuTitle;
		
	}
	
	@Override
	public String getTitle() {
		return this.menuTitle;
	}

	@Override
	public void addOption(ConsoleOption option) {
		this.menuOptions.add(option);
	}

	@Override
	public void removeOption(ConsoleOption option) {
		this.menuOptions.remove(option);
	}

	@Override
	public void invokeOption(Integer optionMappedKey) {
		this.menuOptions.stream().forEach(o -> {
			if (o.isMappedNumberEqualTo(optionMappedKey)) {
				o.getAction().doAction();
			}
		});
		
	}

	@Override
	public void displayOptions() {
		this.menuOptions.add(exitOption);
		this.menuOptions.stream().forEach(ConsoleOption::displayOption);
	}

}
