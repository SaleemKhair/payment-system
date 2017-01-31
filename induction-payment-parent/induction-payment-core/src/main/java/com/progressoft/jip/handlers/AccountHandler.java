package com.progressoft.jip.handlers;

import com.progressoft.jip.beans.Account;
import com.progressoft.jip.datastructures.Amount;
import com.progressoft.jip.handlers.exceptions.ValidationException;

public interface AccountHandler {

	void debit(Account account, Amount amount);

	void credit(Account account, Amount amount);

	void validateAccount(Account account) throws ValidationException;

}