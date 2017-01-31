package com.progressoft.jip.rules;

import java.time.LocalDate;

public interface Rule {

	public boolean satistfy(LocalDate paymentDate );

}
