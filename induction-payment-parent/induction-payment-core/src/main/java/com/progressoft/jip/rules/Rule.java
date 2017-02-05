package com.progressoft.jip.rules;

import java.time.LocalDate;

@FunctionalInterface
public interface Rule {

	public boolean satistfy(LocalDate paymentDate);

}
