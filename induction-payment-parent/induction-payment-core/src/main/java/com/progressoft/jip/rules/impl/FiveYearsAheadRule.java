package com.progressoft.jip.rules.impl;

import java.time.LocalDate;

import com.progressoft.jip.rules.Rule;

public class FiveYearsAheadRule implements Rule {

	@Override
	public boolean satistfy(LocalDate paymentDate) {
		LocalDate today = LocalDate.now();
		return !isPastDay(paymentDate, today) && !isExpired(paymentDate, today);
	}

	private boolean isExpired(LocalDate paymentDate, LocalDate today) {
		LocalDate expirationDate = today.plusYears(5);
		return isPaymentYearOutOfRange(paymentDate, expirationDate);
	}

	private boolean isPaymentYearOutOfRange(LocalDate paymentDate, LocalDate expirationDate) {
		return paymentDate.getYear() - expirationDate.getYear() > 0;
	}

	private boolean isPastDay(LocalDate paymentDate, LocalDate today) {
		return paymentDate.isBefore(today);
	}

}
