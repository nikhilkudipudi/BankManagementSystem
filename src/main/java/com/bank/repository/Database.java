package com.bank.repository;

import com.bank.exception.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private Database() { }
    public static Connection connection() {
        try { return DriverManager.getConnection(required("DB_URL"), required("DB_USER"), required("DB_PASSWORD")); }
        catch (SQLException e) { throw new DataAccessException("Unable to connect to MySQL", e); }
    }
    private static String required(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) throw new IllegalStateException(key + " environment variable is required");
        return value;
    }
}
