package com.progressoft.jip.behaviors;

import java.util.Collection;

import com.progressoft.jip.gateways.views.AccountView;

public interface AccountGatewayDBBehaviorsFactory {

    Behavior<AccountView> loadAccountByIBAN();

    Behavior<Collection<AccountView>> loadAccounts();

	Behavior<Void> updateAccount();

	Behavior<Void> createAccount();

}
