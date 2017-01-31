package com.progressoft.jip.report.impl;

import java.io.PrintStream;
import java.util.Collection;

import com.progressoft.jip.beans.PaymentRequest;
import com.progressoft.jip.report.ReportStructure;
import com.progressoft.jip.report.ReportWriter;
import com.thoughtworks.xstream.XStream;

public class XMLReportWriter implements ReportWriter {


    @Override
    public void write(String accountIBAN, Collection<PaymentRequest> paymentRequests, PrintStream writer) {

	XStream xstream = new XStream();
	xstream.autodetectAnnotations(true);
	xstream.processAnnotations(ReportStructure.class);

	ReportStructure report = new ReportStructure(accountIBAN);

	for (PaymentRequest request : paymentRequests) {
	    report.addRequest(request);
	}

	writer.println(xstream.toXML(report));

    }

}
