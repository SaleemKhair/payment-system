package com.progressoft.jip.behaviors;

import java.util.Collection;

import com.progressoft.jip.gateways.views.PaymentPurposeView;

public interface PaymentPurposeBehaviorsFactory {
    
    Behavior<PaymentPurposeView> loadPaymentPurposeByCodeBehavior();

    Behavior<Void> insertPaymentPurposeBehavior();

    Behavior<Collection<PaymentPurposeView>> loadPaymentPurposesBehavior();

    Behavior<Void> deletePaymentPurposeByCodeBehavior();

    Behavior<Void> updatePaymentPurposeNameBehavior();

}
