package com.progressoft.jip.gateways.jpa;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.progressoft.jip.entities.PaymentRequestEntity;
import com.progressoft.jip.gateways.PaymentRequestGateway;
import com.progressoft.jip.gateways.jpa.converters.BeanToEntityConverter;
import com.progressoft.jip.gateways.jpa.converters.EntityToBeanConverter;
import com.progressoft.jip.gateways.views.PaymentRequestView;
import com.progressoft.jip.jparepositories.PaymentRequestJpaRepository;

public class PaymentRequestJpaGateway implements PaymentRequestGateway {

	private EntityToBeanConverter beanConverter;
	private BeanToEntityConverter entityConverter;
	private PaymentRequestJpaRepository paymentRequestJpaRepository;

	public PaymentRequestJpaGateway(EntityToBeanConverter beanConverter, BeanToEntityConverter entityConverter,
			PaymentRequestJpaRepository paymentRequestJpaRepository) {
		this.beanConverter = beanConverter;
		this.entityConverter = entityConverter;
		this.paymentRequestJpaRepository = paymentRequestJpaRepository;
	}

	@Override
	public void deletePaymentRequestById(int id) {
		paymentRequestJpaRepository.deletePaymentRequestById(id);
	}

	@Override
	public PaymentRequestView loadPaymentRequestById(int id) {
		PaymentRequestEntity loadPaymentRequestById = paymentRequestJpaRepository.loadPaymentRequestById(id);
		return beanConverter.toPaymentRequestBean(loadPaymentRequestById);
	}

	@Override
	public void insertPaymentRequest(PaymentRequestView paymentRequest) {
		PaymentRequestEntity paymentRequestEntity = entityConverter.toPaymentRequestEntity(paymentRequest);
		paymentRequestJpaRepository.insertPaymentRequest(paymentRequestEntity);
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequests() {
		Collection<PaymentRequestEntity> loadPaymentRequests = paymentRequestJpaRepository.loadPaymentRequests();
		List<PaymentRequestView> paymentRequestViews = new ArrayList<>();
		loadPaymentRequests.stream().forEach(p -> paymentRequestViews.add(beanConverter.toPaymentRequestBean(p)));
		return paymentRequestViews;
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequestsByOrderingAccountIban(String iban) {
		Collection<PaymentRequestEntity> loadPaymentRequestsByOrderingAccountIBAN = paymentRequestJpaRepository
				.loadPaymentRequestsByOrderingAccountIBAN(iban);
		List<PaymentRequestView> paymentRequestViews = new ArrayList<>();
		loadPaymentRequestsByOrderingAccountIBAN.stream()
				.forEach(p -> paymentRequestViews.add(beanConverter.toPaymentRequestBean(p)));
		return paymentRequestViews;
	}

	@Override
	public Collection<PaymentRequestView> loadPaymentRequestsByPaymentDate(LocalDate paymentDate) {
		Collection<PaymentRequestEntity> loadPaymentRequestByPaymentDate = paymentRequestJpaRepository
				.loadPaymentRequestByPaymentDate(
						Date.from(paymentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		List<PaymentRequestView> paymentRequestViews = new ArrayList<>();
		loadPaymentRequestByPaymentDate.stream()
				.forEach(p -> paymentRequestViews.add(beanConverter.toPaymentRequestBean(p)));
		return paymentRequestViews;
	}

	@Override
	public void updatePaymentRequest(PaymentRequestView request) {
		PaymentRequestEntity paymentRequestEntity = entityConverter.toPaymentRequestEntity(request);
		paymentRequestJpaRepository.updatePaymentRequest(paymentRequestEntity);
	}

}
