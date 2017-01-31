package com.progressoft.jip.gateways.sql.impl;

import java.util.Collection;

import javax.sql.DataSource;

import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.behaviors.PaymentPurposeBehaviorsFactory;
import com.progressoft.jip.gateways.PaymentPurposeGateway;
import com.progressoft.jip.gateways.sql.AbstractGateway;
import com.progressoft.jip.gateways.views.PaymentPurposeView;



public class MySqlPaymentPurposeGateway extends AbstractGateway implements PaymentPurposeGateway {

    private final Behavior<PaymentPurposeView> loadPaymentPurposeByCode;
    private final Behavior<Void> insertPaymentPurpose;
    private final Behavior<Collection<PaymentPurposeView>> loadPaymentPurposes;
    private final Behavior<Void> deletePaymentPurposeByCode;
    private final Behavior<Void> updatePaymentPurposeName;

    public MySqlPaymentPurposeGateway(DataSource dataSource, PaymentPurposeBehaviorsFactory factory) {
	super(dataSource);
	this.insertPaymentPurpose = factory.insertPaymentPurposeBehavior();
	this.loadPaymentPurposeByCode = factory.loadPaymentPurposeByCodeBehavior();
	this.loadPaymentPurposes = factory.loadPaymentPurposesBehavior();
	this.deletePaymentPurposeByCode = factory.deletePaymentPurposeByCodeBehavior();
	this.updatePaymentPurposeName = factory.updatePaymentPurposeNameBehavior();
    }

    @Override
    public PaymentPurposeView loadPaymentPurposeByCode(String code) {
	return loadPaymentPurposeByCode.execute(dataSource, code);
    }

    @Override
    public void insertPaymentPurpose(PaymentPurposeView paymentPurposeDataStructure) {
	insertPaymentPurpose.execute(dataSource, paymentPurposeDataStructure);
    }

    @Override
    public Collection<PaymentPurposeView> loadPaymentPurposes() {
	return loadPaymentPurposes.execute(dataSource);
    }

    @Override
    public void deletePaymentPurposeByCode(String code) {
	deletePaymentPurposeByCode.execute(dataSource, code);
    }

    @Override
    public void updatePaymentPurposeName(PaymentPurposeView paymentPurpose) {
	updatePaymentPurposeName.execute(dataSource, paymentPurpose.getCode(), paymentPurpose.getName());
    }

}
