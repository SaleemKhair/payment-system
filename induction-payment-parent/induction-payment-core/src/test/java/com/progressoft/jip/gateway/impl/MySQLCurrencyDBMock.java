package com.progressoft.jip.gateway.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import com.progressoft.jip.utilities.Constants;

public class MySQLCurrencyDBMock {

	private static final String INSERT_INTO_VALUES = "INSERT INTO ? VALUES(?,?,?)";
	private static final String DELETE_DEL = "delete from ? where CODE = 'DEL'";
	private static final String DELETE_CURRENCY_UPD = "delete from ? where CODE = 'UPD'";
	public static final String UPDATED_CRNCY_CODE = "UPD";
	public static final String DELETED_CRNCY_CODE = "DEL";

	public static void setUpDBEnviroment(BasicDataSource dataSource) {
		try (Connection connection = dataSource.getConnection()) {

			insertCurrencyIntoTable(connection, DELETED_CRNCY_CODE, "Delete Currency", 0.2);
			insertCurrencyIntoTable(connection, UPDATED_CRNCY_CODE, "Update Currency", 0.4);

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public static void tearDownDBEnviroment(BasicDataSource dataSource) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_CURRENCY_UPD);
				PreparedStatement statement2 = connection.prepareStatement(DELETE_DEL)) {
			statement.setString(1, Constants.CRNCY_TABLE_NAME);
			statement2.setString(1, Constants.CRNCY_TABLE_NAME);
			statement.executeUpdate();
			statement2.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private static void insertCurrencyIntoTable(Connection connection, String code, String name, double rate)
			throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_VALUES)) {
			statement.setString(1, Constants.CRNCY_TABLE_NAME);
			statement.setString(2, code);
			statement.setString(3, name);
			statement.setDouble(4, rate);
			statement.executeUpdate();
		}
	}

	public static double selectUpdatedCurrencyRateValue(BasicDataSource dataSource) {
		try {
			ResultSet resultSet = selectCurrencyByCode(dataSource, UPDATED_CRNCY_CODE);
			resultSet.next();
			return resultSet.getDouble(Constants.CRNCY_RATE_COLOMN);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public static boolean isCurrencyRecordAvilable(BasicDataSource dataSource, String code) {
		try {
			ResultSet resultSet = selectCurrencyByCode(dataSource, code);
			return resultSet.isBeforeFirst();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private static ResultSet selectCurrencyByCode(BasicDataSource dataSource, String code) {
		String sql = "SELECT * FROM " + Constants.CRNCY_TABLE_NAME + " WHERE " + Constants.CRNCY_CODE_COLOMN + " = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, code);
			return statement.executeQuery();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
