package com.progressoft.jip.gateways.views;

import java.math.BigDecimal;
import java.sql.Date;

public interface PaymentRequestView {

	int getId();

	String getOrderingAccountIban();

	String getBeneficiaryAccountIban();

	String getBeneficiaryName();

	BigDecimal getPaymentAmount();

	String getCurrencyCode();

	String getPurposeCode();

	Date getPaymentDate();

	String getAmountInWords();

}