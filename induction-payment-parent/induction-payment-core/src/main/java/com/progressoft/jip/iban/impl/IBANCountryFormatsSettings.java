package com.progressoft.jip.iban.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "index")
@XmlAccessorType(XmlAccessType.FIELD)
public class IBANCountryFormatsSettings {
    @XmlElement(name = "countryNameIndex")
    private int countryNameIndex;
    @XmlElement(name = "countryCodeIndex")
    private int countryCodeIndex;
    @XmlElement(name = "ibanFormatIndex")
    private int ibanFormatIndex;
    @XmlElement(name = "ibanLengthIndex")
    private int ibanLengthIndex;

    public void setCountryNameIndex(int countryNameIndex) {
	this.countryNameIndex = countryNameIndex;
    }

    public void setCountryCodeIndex(int countryCodeIndex) {
	this.countryCodeIndex = countryCodeIndex;
    }

    public void setIbanFormatIndex(int ibanFormatIndex) {
	this.ibanFormatIndex = ibanFormatIndex;
    }

    public void setIbanLength(int ibanLengthIndex) {
	this.ibanLengthIndex = ibanLengthIndex;
    }

    public int getCountryNameIndex() {
	return countryNameIndex;
    }

    public int getCountryCodeIndex() {
	return countryCodeIndex;
    }

    public int getIbanFormatIndex() {
	return ibanFormatIndex;
    }

    public int getIbanLengthIndex() {
	return ibanLengthIndex;
    }

    public int getIndexByName(String columnName) {

	if ("countryCodeIndex".equals(columnName))
	    return countryCodeIndex;
	if ("countryNameIndex".equals(columnName))
	    return countryNameIndex;
	if ("ibanFormatIndex".equals(columnName))
	    return ibanFormatIndex;
		if ("ibanLengthIndex".equals(columnName))
	    return ibanLengthIndex;
	return -1;
    }
}
