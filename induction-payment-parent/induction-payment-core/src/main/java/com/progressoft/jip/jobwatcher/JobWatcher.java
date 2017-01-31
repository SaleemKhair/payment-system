package com.progressoft.jip.jobwatcher;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

import com.progressoft.jip.handlers.PaymentRequestHandler;
import com.progressoft.jip.repository.CurrencyExchangeRateRepository;
import com.progressoft.jip.repository.PaymentRequestRepository;
import com.progressoft.jip.usecases.AccountUseCases;

public class JobWatcher extends TimerTask {

	public static final long ONCE_A_DAY = 24 * 60 * 60 * 1000L;

	private CurrencyExchangeRateRepository currencyExchangeRateRepository;
	private PaymentRequestHandler handler;
	private AccountUseCases accountUseCases;
	private PaymentRequestRepository paymentRequestRepository;
	private Timer t = new Timer();

	private JobWatcher(CurrencyExchangeRateRepository currencyExchangeRateRepository, long period) {
		this.currencyExchangeRateRepository = currencyExchangeRateRepository;
		startCronJobSchedule(period);
	}

	public JobWatcher(CurrencyExchangeRateRepository currencyExchangeRateRepository) {
		this(currencyExchangeRateRepository, ONCE_A_DAY);
	}

	private void startCronJobSchedule(long period) {
		new Thread(() -> {
			t.scheduleAtFixedRate(this, 0L, period);
		}).start();
	}

	@Override
	public synchronized void run() {
		paymentRequestRepository.loadPaymentRequestsByPaymentDate(LocalDate.now());

	}
}
