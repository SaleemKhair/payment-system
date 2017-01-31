package com.progressoft.jip.rules.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class FiveYearsAheadRuleTest {

	FiveYearsAheadRule rule = new FiveYearsAheadRule();

	@Test
	public void givenPaymentDateInRange_CallingSatisfyRule_ShouldReturnTrue() {
		assertTrue(rule.satistfy(LocalDate.now().plusYears(2)));
	}

	@Test
	public void givenPaymentDateAfterFiveYears_CallingSatisfyRule_ShouldReturnFalse() {
		assertFalse(rule.satistfy(LocalDate.now().plusYears(7)));
	}

	@Test
	public void givenPastPaymentDate_CallingSatisfyRule_ShouldReturnFalse() {
		assertFalse(rule.satistfy(LocalDate.now().plusYears(-1)));
	}

}
