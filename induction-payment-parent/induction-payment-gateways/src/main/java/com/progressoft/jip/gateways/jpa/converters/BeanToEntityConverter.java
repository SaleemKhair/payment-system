package com.progressoft.jip.gateways.jpa.converters;

import com.progressoft.jip.entities.AccountEntity;
import com.progressoft.jip.entities.CurrencyEntity;
import com.progressoft.jip.entities.PaymentPurposeEntity;
import com.progressoft.jip.entities.PaymentRequestEntity;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.gateways.views.CurrencyView;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.gateways.views.PaymentRequestView;

public interface BeanToEntityConverter {

	AccountEntity toAccountEntity(AccountView accountView);

	PaymentRequestEntity toPaymentRequestEntity(PaymentRequestView paymentRequestView);

	PaymentPurposeEntity toPaymentPurposeEntity(PaymentPurposeView paymentPurposeView);

	CurrencyEntity toCurrencyEntity(CurrencyView currencyView);

}