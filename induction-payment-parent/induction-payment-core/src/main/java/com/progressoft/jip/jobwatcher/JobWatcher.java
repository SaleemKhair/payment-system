package com.progressoft.jip.jobwatcher;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.handlers.AccountHandler;
import com.progressoft.jip.handlers.PaymentRequestHandler;
import com.progressoft.jip.repository.AccountRepository;
import com.progressoft.jip.repository.PaymentRequestRepository;

public class JobWatcher extends TimerTask {

	public static final long ONCE_A_DAY = 24 * 60 * 60 * 1000L;

	private PaymentRequestHandler paymentRequestHandler;
	private AccountHandler accountHandler;
	private AccountRepository accountRepository;
	private PaymentRequestRepository paymentRequestRepository;
	private Timer timer = new Timer();

	public JobWatcher(PaymentRequestHandler paymentHandler, AccountRepository accountRepository,
			PaymentRequestRepository paymentRequestRepository, AccountHandler accountHandler) {
		this.paymentRequestHandler = paymentHandler;
		this.accountRepository = accountRepository;
		this.paymentRequestRepository = paymentRequestRepository;
		this.accountHandler = accountHandler;
	}

	public void startCronJobSchedule(long period) {
		new Thread(() -> timer.scheduleAtFixedRate(this, 0L, period)).start();
	}

	public void startCronJobSchedule() {
		startCronJobSchedule(ONCE_A_DAY);
	}

	public void checkNow() {
		Collection<PaymentRequest> requestsByPaymentDate = paymentRequestRepository
				.loadPaymentRequestsByPaymentDate(LocalDate.now());
		for (PaymentRequest paymentRequest : requestsByPaymentDate) {
			if (!"Submitted".equals(paymentRequest.getSubmissionState())) {
				paymentRequestHandler.preformPayment(paymentRequest, accountHandler,
						accountRepository.loadAccountByIban(paymentRequest.getOrderingAccountIban()));
			}
		}
	}

	@Override
	public synchronized void run() {
		checkNow();
	}

	public void shutDown() {
		timer.cancel();
	}
}
