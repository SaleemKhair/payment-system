package com.progressoft.jip.jparepositories;

import java.util.Collection;

import com.progressoft.jip.entities.PaymentPurposeEntity;

public interface PaymentPurposeJpaRepository {

	PaymentPurposeEntity loadPaymentPurposeByCode(String code);

	void insertPaymentPurpose(PaymentPurposeEntity paymentPurpose);

	Collection<PaymentPurposeEntity> loadPaymentPurposes();

	void deletePaymentPurposeByCode(String code);

	void updatePaymentPurposeName(PaymentPurposeEntity paymentPurpose);

}