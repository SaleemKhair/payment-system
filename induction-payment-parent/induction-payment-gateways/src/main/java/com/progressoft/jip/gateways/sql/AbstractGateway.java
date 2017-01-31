package com.progressoft.jip.gateways.sql;

import java.util.Objects;

import javax.sql.DataSource;

import com.progressoft.jip.gateways.exceptions.NullDataSourceException;


public abstract class AbstractGateway {
    protected DataSource dataSource;

    public AbstractGateway(DataSource dataSource) {
	if (Objects.isNull(dataSource))
	    throw new NullDataSourceException();
	this.dataSource = dataSource;
    }
}
