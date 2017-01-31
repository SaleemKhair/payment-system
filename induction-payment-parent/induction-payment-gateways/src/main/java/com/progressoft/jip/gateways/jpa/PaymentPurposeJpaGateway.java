package com.progressoft.jip.gateways.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.entities.PaymentPurposeEntity;
import com.progressoft.jip.gateways.PaymentPurposeGateway;
import com.progressoft.jip.gateways.jpa.converters.BeanToEntityConverter;
import com.progressoft.jip.gateways.jpa.converters.EntityToBeanConverter;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.jparepositories.PaymentPurposeJpaRepository;

public class PaymentPurposeJpaGateway implements PaymentPurposeGateway {
	private EntityToBeanConverter beanConverter;
	private BeanToEntityConverter entityConverter;
	private PaymentPurposeJpaRepository paymentPurposeJpaRepository;

	public PaymentPurposeJpaGateway(EntityToBeanConverter beanConverter, BeanToEntityConverter entityConverter,
			PaymentPurposeJpaRepository paymentPurposeJpaRepository) {
		this.beanConverter = beanConverter;
		this.entityConverter = entityConverter;
		this.paymentPurposeJpaRepository = paymentPurposeJpaRepository;
	}

	@Override
	public PaymentPurposeView loadPaymentPurposeByCode(String code) {
		PaymentPurposeEntity loadPaymentPurposeByCode = paymentPurposeJpaRepository.loadPaymentPurposeByCode(code);
		return beanConverter.toPaymentPurposeBean(loadPaymentPurposeByCode);
	}

	@Override
	public void insertPaymentPurpose(PaymentPurposeView paymentPurposeDataStructure) {
		PaymentPurposeEntity paymentPurposeEntity = entityConverter.toPaymentPurposeEntity(paymentPurposeDataStructure);
		paymentPurposeJpaRepository.insertPaymentPurpose(paymentPurposeEntity);
	}

	@Override
	public Collection<PaymentPurposeView> loadPaymentPurposes() {
		Collection<PaymentPurposeEntity> loadPaymentPurposes = paymentPurposeJpaRepository.loadPaymentPurposes();
		List<PaymentPurposeView> purposeViews = new ArrayList<>();
		loadPaymentPurposes.stream().forEach(a -> purposeViews.add(beanConverter.toPaymentPurposeBean(a)));
		return purposeViews;
	}

	@Override
	public void deletePaymentPurposeByCode(String code) {
		paymentPurposeJpaRepository.deletePaymentPurposeByCode(code);
	}

	@Override
	public void updatePaymentPurposeName(PaymentPurposeView paymentPurpose) {
		PaymentPurposeEntity paymentPurposeEntity = entityConverter.toPaymentPurposeEntity(paymentPurpose);
		paymentPurposeJpaRepository.updatePaymentPurposeName(paymentPurposeEntity);
	}

}
