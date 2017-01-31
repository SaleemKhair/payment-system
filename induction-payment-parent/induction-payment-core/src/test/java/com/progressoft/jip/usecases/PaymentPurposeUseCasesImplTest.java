package com.progressoft.jip.usecases;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.beans.PaymentPurpose;
import com.progressoft.jip.gateways.exceptions.EmptyPaymentPurposeCodeException;
import com.progressoft.jip.gateways.views.PaymentPurposeView;
import com.progressoft.jip.handlers.exceptions.PurposeValidationException;
import com.progressoft.jip.handlers.impl.PaymentPurposeHandlerImpl;
import com.progressoft.jip.handlers.validators.impl.PaymentPurposeValidator;
import com.progressoft.jip.repository.PaymentPurposeRepository;
import com.progressoft.jip.repository.impl.PaymentPurposeRepositoryImpl;
import com.progressoft.jip.usecases.exceptions.InvalidPaymentPurposeCodeException;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeCodeException;
import com.progressoft.jip.usecases.exceptions.NullPaymentPurposeNameException;
import com.progressoft.jip.usecases.impl.PaymentPurposeUseCasesImpl;

public class PaymentPurposeUseCasesImplTest {

	private static final String NAME = "NAME";
	private static final String CODE = "CODE";
	private static final String SALARY = "SALARY";
	private static final String SALA = "SALA";
	private PaymentPurposeUseCases useCases;

	@Before
	public void setup() {
		PaymentPurposeGatewayMock gatewayMock = new PaymentPurposeGatewayMock();
		PaymentPurposeRepository repository = new PaymentPurposeRepositoryImpl(gatewayMock);
		PaymentPurposeHandlerImpl handler = new PaymentPurposeHandlerImpl(new PaymentPurposeValidator());
		useCases = new PaymentPurposeUseCasesImpl(repository, handler);
	}

	@Test
	public void givenPaymentPurposeUseCases_CallingLoadPaymentPurposes_ShouldReturnAllPurposes() {
		Collection<PaymentPurposeView> purposes = useCases.getAllPaymentPurposes();
		assertEquals(2, purposes.size());
	}

	@Test
	public void givenPaymentPurposeUseCases_CallingLoadPaymentPurposeByCodeUsingAvailableCode_ShouldReturnPurpose() {
		PaymentPurposeView purpose = useCases.getPaymentPurpose(SALA);
		assertEquals(SALARY, purpose.getName());
	}

	@Test
	public void givenPaymentPurposeUseCases_CallingDeletePaymentPurposeByCodeUsingAvailableCode_ShouldDeletePurpose() {
		useCases.deletePaymentPurpose(SALA);
		Collection<PaymentPurposeView> purposes = useCases.getAllPaymentPurposes();
		assertEquals(1, purposes.size());
	}

	@Test
	public void givenPaymentPurposeUseCases_CallingCreatePaymentPurpose_ShouldCreatePurpose()
			throws PurposeValidationException {
		useCases.createPaymentPurpose(newPurpose(CODE, NAME));
		PaymentPurposeView purpose = useCases.getPaymentPurpose(CODE);
		assertEquals(NAME, purpose.getName());
	}

	@Test(expected = NullPaymentPurposeCodeException.class)
	public void givenPaymentPurposeUseCases_CallingCreatePaymentPurposeWithNullCode_ShouldThrowNullPaymentPurposeCodeException()
			throws PurposeValidationException {
		useCases.createPaymentPurpose(newPurpose(null, NAME));
	}

	@Test(expected = EmptyPaymentPurposeCodeException.class)
	public void givenPaymentPurposeUseCases_CallingCreatePaymentPurposeWithEmptyCode_ShouldThrowEmptyPaymentPurposeCodeException()
			throws PurposeValidationException {
		useCases.createPaymentPurpose(newPurpose("", NAME));
	}

	@Test(expected = InvalidPaymentPurposeCodeException.class)
	public void givenPaymentPurposeUseCases_CallingCreatePaymentPurposeWithInvalidCode_ShouldThrowInvalidPaymentPurposeCodeException()
			throws PurposeValidationException {
		useCases.createPaymentPurpose(newPurpose("saleeeeeem:D", NAME));
	}

	@Test(expected = NullPaymentPurposeNameException.class)
	public void givenPaymentPurposeUseCases_CallingCreatePaymentPurposeWithNullName_ShouldThrowNullPaymentPurposeNameException()
			throws PurposeValidationException {
		useCases.createPaymentPurpose(newPurpose(CODE, null));
	}

	@Test
	public void givenPaymentPurposeUseCases_CallingEditPaymentPurpose_ShouldEditPurpose()
			throws PurposeValidationException {
		useCases.editPaymentPurpose(newPurpose(SALA, NAME));
		PaymentPurposeView purpose = useCases.getPaymentPurpose(SALA);
		assertEquals(NAME, purpose.getName());
	}

	private PaymentPurpose newPurpose(String code, String name) {
		PaymentPurpose newPurpose = new PaymentPurpose();
		newPurpose.setCode(code);
		newPurpose.setName(name);
		return newPurpose;
	}

}
