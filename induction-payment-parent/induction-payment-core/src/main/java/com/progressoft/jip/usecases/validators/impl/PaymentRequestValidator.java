package com.progressoft.jip.usecases.validators.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.handlers.exceptions.AccountRuleViolationException;
import com.progressoft.jip.handlers.exceptions.InvalidAmountException;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;
import com.progressoft.jip.handlers.exceptions.ValidationException;
import com.progressoft.jip.handlers.validators.Validator;
import com.progressoft.jip.iban.IBANGeneralValidator;
import com.progressoft.jip.iban.exception.InvalidIBANException;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.PaymentPurposeRepository;
import com.progressoft.jip.rules.impl.IPaymentRules;
import com.progressoft.jip.rules.impl.PaymentRules;

public class PaymentRequestValidator implements Validator<PaymentRequest, ValidationException> {

	private AccountRepository accountRepository;
	private PaymentPurposeRepository purposeRepository;
	private IPaymentRules rules;
	private Validator<String, InvalidIBANException> ibanValidator;
	private Validator<PaymentPurpose, PurposeValidationException> purposeValidator;

	public PaymentRequestValidator(IPaymentRules rules, AccountRepository accountRepository,
			PaymentPurposeRepository purposeRepository, Validator<String, InvalidIBANException> ibanValidator,
			Validator<PaymentPurpose, PurposeValidationException> purposeValidator) {
		this.rules = rules;
		this.accountRepository = accountRepository;
		this.purposeRepository = purposeRepository;
		this.ibanValidator = ibanValidator;
		this.purposeValidator = purposeValidator;
	}

	public PaymentRequestValidator(AccountRepository accountRepository, PaymentPurposeRepository purposeRepository) {
		this(PaymentRules.getInstance(), accountRepository, purposeRepository, new IBANGeneralValidator(),
				new PaymentPurposeValidator());
	}

	private void checkPaymentRule(Account account, LocalDate paymentDate) throws AccountRuleViolationException {
		if (!rules.getRule(account.getRule()).satistfy(paymentDate))
			throw new AccountRuleViolationException(account.getRule());
	}

	private void validateBeneficiaryIban(String iban) throws InvalidIBANException {
		ibanValidator.validate(iban);
	}

	private void isValidAmount(BigDecimal amount) {
		if (amount.doubleValue() < 0) {
			throw new InvalidAmountException();
		}
	}

	@Override
	public void validate(PaymentRequest paymentRequest)
			throws AccountRuleViolationException, InvalidIBANException, PurposeValidationException {
		isValidAmount(paymentRequest.getPaymentAmount());
		PaymentPurpose purpose = purposeRepository.loadPaymentPurposeByCode(paymentRequest.getPurposeCode());
		purposeValidator.validate(purpose);
		checkPaymentRule(accountRepository.loadAccountByIban(paymentRequest.getOrderingAccountIban()),
				LocalDate.parse(paymentRequest.getPaymentDate().toString()));
		validateBeneficiaryIban(paymentRequest.getBeneficiaryAccountIban());
	}

}
