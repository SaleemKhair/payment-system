package com.progressoft.jip.jparepositories.impl;

public class Void {

	private static final Void VOID = new Void();

	private Void() {
	}

	public static Void getVoid() {
		return VOID;
	}

}
