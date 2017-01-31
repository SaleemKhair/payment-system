package com.progressoft.jip.gateway.sql;

import java.time.LocalDate;
import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.behaviors.PaymentRequestBehaviorsFactory;
import com.progressoft.jip.gateway.AbstractGateway;
import com.progressoft.jip.gateway.PaymentRequestGateway;
import com.progressoft.jip.gateways.views.PaymentRequestView;

public class MySqlPaymentRequestGateway extends AbstractGateway implements PaymentRequestGateway {

	private Behavior<PaymentRequestView> loadPaymentRequestById;
	private Behavior<Void> deletePaymentRequestById;
	private Behavior<Void> insertPaymentRequest;
	private Behavior<Collection<PaymentRequestView>> loadPaymentRequests;
	private Behavior<Collection<PaymentRequestView>> loadPaymentRequestsByOrderingAccIBAN;
	private Behavior<Collection<PaymentRequestView>> loadPaymentRequestsByPaymentDate;
	private Behavior<Void> updatePaymentRequest;

	public MySqlPaymentRequestGateway(DataSource dataSource, PaymentRequestBehaviorsFactory factory) {
		super(dataSource);
		this.loadPaymentRequestById = factory.loadPaymentRequestById();
		this.deletePaymentRequestById = factory.deletePaymentRequestById();
		this.insertPaymentRequest = factory.insertPaymentRequest();
		this.loadPaymentRequests = factory.loadPaymentRequests();
		this.loadPaymentRequestsByOrderingAccIBAN = factory.loadPaymentRequestsByOrderingAccIBAN();
		this.loadPaymentRequestsByPaymentDate = factory.loadPaymentRequestsByPaymentDate();
		this.updatePaymentRequest = factory.updatePaymentRequest();
	}

	@Override
	public void deletePaymentRequestById(int id) {
		deletePaymentRequestById.execute(dataSource, id);
	}

	@Override
	public PaymentRequestView loadPaymentRequestById(int id) {
		return loadPaymentRequestById.execute(dataSource, id);
	}

	@Override
	public void insertPaymentRequest(PaymentRequestView paymentRequestDataStructure) {
		insertPaymentRequest.execute(dataSource, paymentRequestDataStructure);
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequests() {
		return loadPaymentRequests.execute(dataSource);
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequestsByOrderingAccountIban(String iban) {
		return loadPaymentRequestsByOrderingAccIBAN.execute(dataSource, iban);
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequestsByPaymentDate(LocalDate now) {
		return loadPaymentRequestsByPaymentDate.execute(dataSource, now);
	}

	@Override
	public void updatePaymentRequest(PaymentRequestView request) {
		updatePaymentRequest.execute(dataSource, request);
	}

}
