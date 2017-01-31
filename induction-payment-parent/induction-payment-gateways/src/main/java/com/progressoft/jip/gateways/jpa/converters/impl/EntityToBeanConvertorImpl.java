package com.progressoft.jip.gateways.jpa.converters.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.Currency;
import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.entities.AccountEntity;
import com.progressoft.jip.entities.CurrencyEntity;
import com.progressoft.jip.entities.PaymentPurposeEntity;
import com.progressoft.jip.entities.PaymentRequestEntity;
import com.progressoft.jip.gateways.jpa.converters.EntityToBeanConverter;
import com.progressoft.jip.gateways.jpa.converters.exceptions.EntityConversionException;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.gateways.views.CurrencyView;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.gateways.views.PaymentRequestView;

public class EntityToBeanConvertorImpl implements EntityToBeanConverter {

	@Override
	public AccountView toAccountBean(AccountEntity accountEntity) {
		Account account = new Account();
		try {
			BeanUtils.copyProperties(account, accountEntity);
			account.setCurrencyCode(accountEntity.getCurrency().getCode());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new EntityConversionException("Problem while converting Account Entity", e);
		}
		return account;
	}

	@Override
	public PaymentPurposeView toPaymentPurposeBean(PaymentPurposeEntity purposeEntity) {
		PaymentPurpose paymentPurpose = new PaymentPurpose();
		try {
			BeanUtils.copyProperties(paymentPurpose, purposeEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new EntityConversionException("Problem while converting PaymentPurpose", e);
		}
		return paymentPurpose;
	}

	@Override
	public PaymentRequestView toPaymentRequestBean(PaymentRequestEntity entity) {
		PaymentRequest paymentRequest = new PaymentRequest();
		try {
			BeanUtils.copyProperties(paymentRequest, entity);
			paymentRequest.setCurrencyCode(entity.getCurrency().getCode());
			paymentRequest.setOrderingAccountIban(entity.getAccount().getIban());
			paymentRequest.setPurposeCode(entity.getPaymentPurpose().getCode());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new EntityConversionException("Problem while converting PaymentRequest", e);
		}

		return paymentRequest;

	}

	@Override
	public CurrencyView toCurrencyBean(CurrencyEntity entity) {
		Currency currency = new Currency();
		try {
			BeanUtils.copyProperties(currency, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new EntityConversionException("Problem while converting Currency", e);
		}
		return currency;
	}

}
