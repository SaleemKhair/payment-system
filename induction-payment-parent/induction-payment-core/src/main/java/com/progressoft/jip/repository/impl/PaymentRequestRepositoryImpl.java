package com.progressoft.jip.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.gateways.PaymentRequestGateway;
import com.progressoft.jip.handlers.exceptions.AccountRuleViolationException;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.repository.exceptions.AccountDoesNotHavePaymentRequestsException;

public class PaymentRequestRepositoryImpl implements PaymentRequestRepository {

	private PaymentRequestGateway gateway;

	public PaymentRequestRepositoryImpl(PaymentRequestGateway gateway) {
		this.gateway = gateway;
	}

	@Override
	public PaymentRequest loadPaymentRequestById(int id) {
		return (PaymentRequest) gateway.loadPaymentRequestById(id);
	}

	@Override
	public void insertPaymentRequest(PaymentRequest paymentRequest) throws AccountRuleViolationException {
		gateway.insertPaymentRequest(paymentRequest);
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequests() {
		ArrayList<PaymentRequest> collection = new ArrayList<>();
		gateway.loadPaymentRequests().stream().forEach(p -> collection.add((PaymentRequest) p));
		return Collections.unmodifiableCollection(collection);
	}

	@Override
	public void deletePaymentRequestById(int id) {
		gateway.deletePaymentRequestById(id);
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequestsByOrderingAccountIban(String iban)
			throws AccountDoesNotHavePaymentRequestsException {
		ArrayList<PaymentRequest> collection = new ArrayList<>();
		gateway.loadPaymentRequestsByOrderingAccountIban(iban).stream()
				.forEach(p -> collection.add((PaymentRequest) p));
		// if (collection.isEmpty()) {
		// throw new AccountDoesNotHavePaymentRequestsException();
		// }
		return Collections.unmodifiableCollection(collection);
	}

	@Override
	public Collection<PaymentRequest> loadPaymentRequestsByPaymentDate(LocalDate executionDate) {
		ArrayList<PaymentRequest> collection = new ArrayList<>();
		gateway.loadPaymentRequestsByPaymentDate(executionDate).stream()
				.forEach(p -> collection.add((PaymentRequest) p));
		return collection;
	}

	@Override
	public void updatePaymentRequest(PaymentRequest request) {
		gateway.updatePaymentRequest(request);
	}

}
