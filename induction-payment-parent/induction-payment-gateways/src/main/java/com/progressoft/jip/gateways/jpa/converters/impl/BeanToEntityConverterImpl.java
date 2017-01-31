package com.progressoft.jip.gateways.jpa.converters.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.progressoft.jip.entities.AccountEntity;
import com.progressoft.jip.entities.CurrencyEntity;
import com.progressoft.jip.entities.PaymentPurposeEntity;
import com.progressoft.jip.entities.PaymentRequestEntity;
import com.progressoft.jip.gateways.jpa.converters.BeanToEntityConverter;
import com.progressoft.jip.gateways.jpa.converters.exceptions.BeanConversionException;
import com.progressoft.jip.gateways.views.AccountView;
import com.progressoft.jip.gateways.views.CurrencyView;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.gateways.views.PaymentRequestView;
import com.progressoft.jip.jparepositories.AccountJpaRepository;
import com.progressoft.jip.jparepositories.CurrencyJpaRepository;
import com.progressoft.jip.jparepositories.PaymentPurposeJpaRepository;

public class BeanToEntityConverterImpl implements BeanToEntityConverter {
	private AccountJpaRepository accountJpaRepository;
	private PaymentPurposeJpaRepository paymentPurposeJpaRepository;
	private CurrencyJpaRepository currencyJpaRepository;

	public BeanToEntityConverterImpl(AccountJpaRepository accountJpaRepository,
			PaymentPurposeJpaRepository paymentPurposeJpaRepository, CurrencyJpaRepository currencyJpaRepository) {
		this.accountJpaRepository = accountJpaRepository;
		this.paymentPurposeJpaRepository = paymentPurposeJpaRepository;
		this.currencyJpaRepository = currencyJpaRepository;
	}

	@Override
	public AccountEntity toAccountEntity(AccountView accountView) {
		AccountEntity accountEntity = new AccountEntity();
		try {
			BeanUtils.copyProperties(accountEntity, accountView);
			String currencyCode = accountView.getCurrencyCode();
			accountEntity.setCurrency(currencyJpaRepository.loadCurrencyByCode(currencyCode));
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanConversionException("Problem While Converting Account Bean", e);
		}
		return accountEntity;
	}

	@Override
	public PaymentRequestEntity toPaymentRequestEntity(PaymentRequestView paymentRequestView) {
		PaymentRequestEntity paymentRequestEntity = new PaymentRequestEntity();
		try {
			BeanUtils.copyProperties(paymentRequestEntity, paymentRequestView);
			String purposeCode = paymentRequestView.getPurposeCode();
			String currencyCode = paymentRequestView.getCurrencyCode();
			String orderingAccountIban = paymentRequestView.getOrderingAccountIban();
			paymentRequestEntity.setPaymentPurpose(paymentPurposeJpaRepository.loadPaymentPurposeByCode(purposeCode));
			paymentRequestEntity.setCurrency(currencyJpaRepository.loadCurrencyByCode(currencyCode));
			paymentRequestEntity.setAccount(accountJpaRepository.loadAccountByIban(orderingAccountIban));
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanConversionException("Problem While Converting Payment Request Bean", e);
		}
		return paymentRequestEntity;
	}

	@Override
	public PaymentPurposeEntity toPaymentPurposeEntity(PaymentPurposeView paymentPurposeView) {
		PaymentPurposeEntity paymentPurposeEntity = new PaymentPurposeEntity();
		try {
			BeanUtils.copyProperties(paymentPurposeEntity, paymentPurposeView);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanConversionException("Problem While Converting Payment Purpose Bean", e);
		}
		return paymentPurposeEntity;
	}

	@Override
	public CurrencyEntity toCurrencyEntity(CurrencyView currencyView) {
		CurrencyEntity currencyEntity = new CurrencyEntity();
		try {
			BeanUtils.copyProperties(currencyEntity, currencyView);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanConversionException("Problem While Converting Currency Bean", e);
		}
		return currencyEntity;
	}
}
