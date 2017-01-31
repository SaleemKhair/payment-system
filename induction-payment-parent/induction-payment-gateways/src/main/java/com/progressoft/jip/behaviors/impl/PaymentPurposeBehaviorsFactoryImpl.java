package com.progressoft.jip.behaviors.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.behaviors.AbstractBehavior;
import com.progressoft.jip.behaviors.Behavior;
import com.progressoft.jip.behaviors.PaymentPurposeBehaviorsFactory;
import com.progressoft.jip.gateways.exceptions.DuplicatePaymentPurposeCodeException;
import com.progressoft.jip.gateways.exceptions.EmptyPaymentPurposeCodeException;
import com.progressoft.jip.gateways.exceptions.NoneExistingPaymentPurposeException;
import com.progressoft.jip.gateways.exceptions.PaymentPurposeNotFoundException;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.jparepositories.exceptions.NullPaymentPurposeException;

public class PaymentPurposeBehaviorsFactoryImpl implements PaymentPurposeBehaviorsFactory {

	private static final String SQL_STATE_DUPLICATE_ENTRY = "23000";

	@Override
	public Behavior<PaymentPurposeView> loadPaymentPurposeByCodeBehavior() {
		return LOAD_PAYMENT_PURPOSE_BY_CODE;
	}

	@Override
	public Behavior<Void> insertPaymentPurposeBehavior() {
		return INSERT_PAYMENT_RURPOSE_BEHAVIOR;
	}

	@Override
	public Behavior<Collection<PaymentPurposeView>> loadPaymentPurposesBehavior() {
		return LOAD_PAYMENT_PURPOSES;
	}

	@Override
	public Behavior<Void> deletePaymentPurposeByCodeBehavior() {
		return DELETE_PAYMENT_PURPOSE_BY_CODE;
	}

	@Override
	public Behavior<Void> updatePaymentPurposeNameBehavior() {
		return UPDATE_PAYMENT_PURPOSE_NAME;
	}

	public static final Behavior<PaymentPurposeView> LOAD_PAYMENT_PURPOSE_BY_CODE = new AbstractBehavior<PaymentPurposeView>() {

		@Override
		public PaymentPurposeView operation() {
			String code = (String) parameters[0];
			// TODO validations should'nt be here
			validateCode(code);
			try {
				ArrayList<PaymentPurposeView> collection = new ArrayList<>();

				runner.query(Constants.LOAD_PAYMENT_PURPOSE_BY_CODE_SQL_STATEMENT,
						new BeanListHandler<>(PaymentPurpose.class), code).stream()
						.forEach(pp -> collection.add((PaymentPurposeView) pp));
				if (collection.isEmpty())
					throw new PaymentPurposeNotFoundException();
				return collection.get(0);
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	};

	private static void validateCode(String code) {
		if (Objects.isNull(code)) {
			throw new NullPaymentPurposeException();
		}
		if (code.isEmpty()) {
			throw new EmptyPaymentPurposeCodeException();
		}
	}

	public static final Behavior<Void> INSERT_PAYMENT_RURPOSE_BEHAVIOR = new AbstractBehavior<Void>() {

		@Override
		public Void operation() {
			PaymentPurposeView paymentPurpose = (PaymentPurposeView) parameters[0];
			validateCode(paymentPurpose.getCode());
			if (Objects.isNull(paymentPurpose.getName())) {
				throw new NullPaymentPurposeException();
			}
			try {
				runner.update(Constants.INSERT_PAYMENT_PURPOSE_SQL_STATEMENT, paymentPurpose.getCode(),
						paymentPurpose.getName());
				return null;
			} catch (SQLException e) {
				if (e.getSQLState().equals(SQL_STATE_DUPLICATE_ENTRY))
					throw new DuplicatePaymentPurposeCodeException();
				throw new IllegalStateException(e);
			}
		}

	};

	public static final Behavior<Collection<PaymentPurposeView>> LOAD_PAYMENT_PURPOSES = new AbstractBehavior<Collection<PaymentPurposeView>>() {

		@Override
		public Collection<PaymentPurposeView> operation() {
			try {
				ArrayList<PaymentPurposeView> collection = new ArrayList<PaymentPurposeView>();
				runner.query(Constants.LOAD_PAYMENT_PURPOSES_SQL_STATEMENT, new BeanListHandler<>(PaymentPurpose.class))
						.stream().forEach(pp -> collection.add((PaymentPurposeView) pp));
				return collection;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	};

	public static final Behavior<Void> DELETE_PAYMENT_PURPOSE_BY_CODE = new AbstractBehavior<Void>() {

		private String code;

		@Override
		public Void operation() {
			code = (String) parameters[0];
			try {
				int effectedRows = runner.update(Constants.DELETE_PAYMENT_PURPOSE_SQL_STATEMENT, code);
				if (effectedRows == 0)
					throw new NoneExistingPaymentPurposeException();
				return null;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	};

	public static final Behavior<Void> UPDATE_PAYMENT_PURPOSE_NAME = new AbstractBehavior<Void>() {

		private String code;
		private String newName;

		@Override
		public Void operation() {
			code = (String) parameters[0];
			newName = (String) parameters[1];
			int effectedRows;
			try {
				effectedRows = runner.update(Constants.UPDATE_PAYMENT_PURPOSE_SQL_STATEMENT, newName, code);
				if (effectedRows == 0)
					throw new NoneExistingPaymentPurposeException();
				return null;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	};

}
