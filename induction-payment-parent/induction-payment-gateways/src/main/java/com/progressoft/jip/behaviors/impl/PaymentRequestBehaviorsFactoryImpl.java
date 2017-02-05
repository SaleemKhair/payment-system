package com.progressoft.jip.behaviors.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.behaviors.AbstractBehavior;
import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.behaviors.PaymentRequestBehaviorsFactory;
import com.progressoft.jip.gateways.exceptions.AccountDoesNotHavePaymentRequestsException;
import com.progressoft.jip.gateways.exceptions.EmptyAccountIBANException;
import com.progressoft.jip.gateways.exceptions.NoneExistingPaymentRequestException;
import com.progressoft.jip.gateways.exceptions.NullAccountIBANException;
import com.progressoft.jip.gateways.views.PaymentRequestView;

public class PaymentRequestBehaviorsFactoryImpl implements PaymentRequestBehaviorsFactory {

	public static final Behavior<PaymentRequestView> LOAD_PAYMENT_REQUEST_BY_ID = new AbstractBehavior<PaymentRequestView>() {

		@Override
		public PaymentRequestView operation() {
			int id = (int) parameters[0];
			try {
				List<PaymentRequestView> list = new ArrayList<>();
				runner.query(Constants.LOAD_PAYMENT_REQUEST_BY_ID_SQL_STATEMENT,
						new BeanListHandler<>(PaymentRequest.class), id).stream()
						.forEach(pr -> list.add((PaymentRequestView) pr));
				if (list.isEmpty())
					throw new NoneExistingPaymentRequestException();
				return list.get(0);
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	};

	public static final Behavior<Void> DELETE_PAYMENT_REQUEST_BY_ID = new AbstractBehavior<Void>() {

		@Override
		public Void operation() {
			int id = (int) parameters[0];
			try {
				int effectedRow = runner.update(Constants.DELETE_PAYMENT_REQUEST_SQL_STATMENT, id);
				if (effectedRow == 0)
					throw new NoneExistingPaymentRequestException();
				return null;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	};

	public static final Behavior<Void> INSERT_PAYMENT_REQUEST = new AbstractBehavior<Void>() {

		@Override
		public Void operation() {
			PaymentRequestView dataStructure = (PaymentRequestView) parameters[0];
			try {
				int effectedRow = runner.update(Constants.INSERT_PAYMENT_REQUEST_SQL,
						dataStructure.getOrderingAccountIban(), dataStructure.getBeneficiaryAccountIban(),
						dataStructure.getBeneficiaryName(), dataStructure.getPaymentAmount(),
						dataStructure.getCurrencyCode(), dataStructure.getPurposeCode(), dataStructure.getPaymentDate(),
						dataStructure.getAmountInWords());
				if (effectedRow == 0)
					throw new NoneExistingPaymentRequestException();
				return null;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	};

	public static final Behavior<Collection<PaymentRequestView>> LOAD_PAYMENT_REQUESTS = new AbstractBehavior<Collection<PaymentRequestView>>() {

		@Override
		public Collection<PaymentRequestView> operation() {
			try {
				List<PaymentRequestView> collection = new ArrayList<>();
				runner.query(Constants.SELECT_ALL_PAYMENT_REQUESTS, new BeanListHandler<>(PaymentRequest.class))
						.stream().forEach(pr -> collection.add((PaymentRequestView) pr));
				return collection;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	};

	public static final Behavior<Collection<PaymentRequestView>> LOAD_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN = new AbstractBehavior<Collection<PaymentRequestView>>() {

		@Override
		public Collection<PaymentRequestView> operation() {
			String iban = (String) parameters[0];
			if (Objects.isNull(iban))
				throw new NullAccountIBANException();
			if (iban.isEmpty())
				throw new EmptyAccountIBANException();
			try {
				List<PaymentRequestView> paymentRequests = new ArrayList<>();
				runner.query(Constants.SELECT_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN,
						new BeanListHandler<>(PaymentRequest.class), iban).stream()
						.forEach(pr -> paymentRequests.add((PaymentRequestView) pr));
				if (paymentRequests.isEmpty())
					throw new AccountDoesNotHavePaymentRequestsException();
				return Collections.unmodifiableCollection(paymentRequests);
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	};

	private static final Behavior<Void> UPDATE_PAYMENT_REQUEST = new AbstractBehavior<Void>() {

		@Override
		public Void operation() {
			PaymentRequestView dataStructure = (PaymentRequestView) parameters[0];
			try {
				int effectedRow = runner.update(Constants.UPDATE_PAYMENT_REQUEST_SQL,
						dataStructure.getOrderingAccountIban(), dataStructure.getBeneficiaryAccountIban(),
						dataStructure.getBeneficiaryName(), dataStructure.getPaymentAmount(),
						dataStructure.getCurrencyCode(), dataStructure.getPurposeCode(), dataStructure.getPaymentDate(),
						dataStructure.getAmountInWords(), dataStructure.getId(), dataStructure.getAmountInWords());
				if (effectedRow == 0)
					throw new NoneExistingPaymentRequestException();
				return null;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	};

	private static final Behavior<Collection<PaymentRequestView>> LOAD_PAYMENT_REQUESTS_BY_PAYMENT_DATE = new AbstractBehavior<Collection<PaymentRequestView>>() {

		@Override
		public Collection<PaymentRequestView> operation() {
			String paymentDate = ((LocalDate) parameters[0]).toString();
			try {
				List<PaymentRequestView> list = new ArrayList<>();
				runner.query(Constants.LOAD_PAYMENT_REQUEST_BY_PAYMENT_DATE,
						new BeanListHandler<>(PaymentRequest.class), paymentDate).stream()
						.forEach(pr -> list.add((PaymentRequestView) pr));
				if (list.isEmpty())
					throw new NoneExistingPaymentRequestException();
				return list;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	};

	@Override
	public Behavior<PaymentRequestView> loadPaymentRequestById() {
		return LOAD_PAYMENT_REQUEST_BY_ID;
	}

	@Override
	public Behavior<Void> deletePaymentRequestById() {
		return DELETE_PAYMENT_REQUEST_BY_ID;
	}

	@Override
	public Behavior<Void> insertPaymentRequest() {
		return INSERT_PAYMENT_REQUEST;
	}

	@Override
	public Behavior<Collection<PaymentRequestView>> loadPaymentRequests() {
		return LOAD_PAYMENT_REQUESTS;
	}

	@Override
	public Behavior<Collection<PaymentRequestView>> loadPaymentRequestsByOrderingAccIBAN() {
		return LOAD_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN;
	}

	@Override
	public Behavior<Collection<PaymentRequestView>> loadPaymentRequestsByPaymentDate() {
		return LOAD_PAYMENT_REQUESTS_BY_PAYMENT_DATE;
	}

	@Override
	public Behavior<Void> updatePaymentRequest() {
		return UPDATE_PAYMENT_REQUEST;
	}

}
