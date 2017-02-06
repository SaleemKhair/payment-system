package com.progressoft.jip.jobwatcher.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.gateways.PaymentRequestGateway;
import com.progressoft.jip.gateways.views.PaymentRequestView;

public class PaymentRequestGatewayMock implements PaymentRequestGateway {
	private Object paymentRequestMonitor = new Object();
	private List<PaymentRequestView> paymentRequests = new ArrayList<>();

	public PaymentRequestGatewayMock() {
		String[] ibans = { "JO94CBJO0010000000000131000302", "AD1200012030200359100100", "AT611904300234573201",
				"BE62510007547061" };
		for (String iban : ibans) {
			PaymentRequest newPaymentRequest = newPaymentRequest(iban);
			paymentRequests.add(newPaymentRequest);
		}
	}

	@Override
	public void deletePaymentRequestById(int id) {
		synchronized (paymentRequestMonitor) {
			
			for (PaymentRequestView paymentRequestView : paymentRequests) {
				if (paymentRequestView.getId() == id) {
					paymentRequests.remove(paymentRequestView);
				}
			}
		}
	}

	@Override
	public PaymentRequestView loadPaymentRequestById(int id) {
		synchronized (paymentRequestMonitor) {
			
			for (PaymentRequestView paymentRequestView : paymentRequests) {
				if (paymentRequestView.getId() == id) {
					return paymentRequestView;
				}
			}
			return null;
		}
	}

	@Override
	public void insertPaymentRequest(PaymentRequestView paymentRequestDataStructure) {
		synchronized (paymentRequestMonitor) {
			paymentRequests.add(paymentRequestDataStructure);
		}
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequests() {
		synchronized (paymentRequestMonitor) {

			return paymentRequests;
		}
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequestsByOrderingAccountIban(String iban) {
		synchronized (paymentRequestMonitor) {
			List<PaymentRequestView> loadedPayments = new ArrayList<>();
			for (PaymentRequestView paymentRequestView : paymentRequests) {
				if (paymentRequestView.getOrderingAccountIban().equals(iban)) {
					loadedPayments.add(paymentRequestView);
				}
			}
			return loadedPayments;
			
		}
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequestsByPaymentDate(LocalDate now) {
		synchronized (paymentRequestMonitor) {
			
			List<PaymentRequestView> loadedPayments = new ArrayList<>();
			for (PaymentRequestView paymentRequestView : paymentRequests) {
				if (LocalDate.parse(paymentRequestView.getPaymentDate().toString()).isEqual(now)) {
					loadedPayments.add(paymentRequestView);
				}
			}
			return Collections.synchronizedCollection(loadedPayments);
		}
	}

	@Override
	public void updatePaymentRequest(PaymentRequestView request) {
		synchronized (paymentRequestMonitor) {
			
			for (PaymentRequestView paymentRequestView : paymentRequests) {
				if (paymentRequestView.getId() == request.getId()) {
					paymentRequests.remove(paymentRequestView);
					paymentRequests.add(request);
				}
			}
		}
	}

	private PaymentRequest newPaymentRequest(String orderingIban) {
		PaymentRequest req = new PaymentRequest();
		req.setBeneficiaryAccountIban("JO94CBJO0010000000000131000302");
		req.setOrderingAccountIban(orderingIban);
		req.setBeneficiaryName("whatever");
		req.setCurrencyCode("JOD");
		req.setPaymentAmount(new BigDecimal("562.4"));
		req.setPurposeCode("SALA");
		req.setPaymentDate(Date.valueOf(LocalDate.of(2017, 1, 2)));
		return req;
	}

}
