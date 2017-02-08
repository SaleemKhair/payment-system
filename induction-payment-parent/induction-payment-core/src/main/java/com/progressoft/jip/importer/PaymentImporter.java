package com.progressoft.jip.importer;

import java.io.InputStream;
import java.text.ParseException;

import com.progressoft.jip.handlers.PaymentImportHandler;

@FunctionalInterface
public interface PaymentImporter {

	void parse(InputStream stream, PaymentImportHandler handler) throws ParseException;

}
