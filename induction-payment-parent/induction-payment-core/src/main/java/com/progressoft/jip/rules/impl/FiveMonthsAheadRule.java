package com.progressoft.jip.rules.impl;

import java.time.LocalDate;

import com.progressoft.jip.rules.Rule;

public class FiveMonthsAheadRule implements Rule {

	@Override
	public boolean satistfy(LocalDate paymentDate) {
		LocalDate today = LocalDate.now();
		return !isPastDay(paymentDate, today) && !isExpired(paymentDate, today);
	}

	private boolean isExpired(LocalDate paymentDate, LocalDate today) {
		LocalDate expirationDate = today.plusMonths(5);
		return isPaymentMonthOutOfRange(paymentDate, expirationDate)
				|| isPaymentYearOutOfRange(paymentDate, expirationDate);
	}

	private boolean isPaymentYearOutOfRange(LocalDate paymentDate, LocalDate expirationDate) {
		return paymentDate.getYear() - expirationDate.getYear() > 0;
	}

	private boolean isPaymentMonthOutOfRange(LocalDate paymentDate, LocalDate expirationDate) {
		return paymentDate.getMonthValue() - expirationDate.getMonthValue() > 0;
	}

	private boolean isPastDay(LocalDate paymentDate, LocalDate today) {
		return paymentDate.isBefore(today);
	}
}
