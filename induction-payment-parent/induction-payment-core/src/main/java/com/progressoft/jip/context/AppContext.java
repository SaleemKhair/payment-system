package com.progressoft.jip.context;

import java.util.List;

import com.progressoft.jip.usecases.AccountUseCases;
import com.progressoft.jip.usecases.CurrencyUseCases;
import com.progressoft.jip.usecases.PaymentPurposeUseCases;
import com.progressoft.jip.usecases.PaymentRequestUseCases;
import com.progressoft.jip.utilities.chequewriting.impl.AbstractAmountWriter;

public interface AppContext {

	PaymentRequestUseCases getPaymentRequestUseCases();

	PaymentPurposeUseCases getPaymentPurposeUseCases();

	CurrencyUseCases getCurrencyUseCases();

	AccountUseCases getAccountUseCases();

	List<String> getPaymentRuleNames();

	AbstractAmountWriter getAbstractAmountWriter();

}