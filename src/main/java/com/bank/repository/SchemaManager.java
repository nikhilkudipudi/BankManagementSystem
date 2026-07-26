package com.bank.repository;

import com.bank.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class SchemaManager {
    private SchemaManager() { }
    public static void createTables() {
        String customers = "CREATE TABLE IF NOT EXISTS customers (id BIGINT AUTO_INCREMENT PRIMARY KEY, full_name VARCHAR(120) NOT NULL, email VARCHAR(160) NOT NULL UNIQUE)";
        String accounts = "CREATE TABLE IF NOT EXISTS accounts (id BIGINT AUTO_INCREMENT PRIMARY KEY, customer_id BIGINT NOT NULL, account_number VARCHAR(32) NOT NULL UNIQUE, account_type VARCHAR(20) NOT NULL, balance DECIMAL(19,2) NOT NULL, FOREIGN KEY (customer_id) REFERENCES customers(id))";
        String transactions = "CREATE TABLE IF NOT EXISTS transactions (id BIGINT AUTO_INCREMENT PRIMARY KEY, account_id BIGINT NOT NULL, transaction_type VARCHAR(20) NOT NULL, amount DECIMAL(19,2) NOT NULL, balance_after DECIMAL(19,2) NOT NULL, reference_note VARCHAR(255), created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (account_id) REFERENCES accounts(id), INDEX ix_transactions_account_created (account_id, created_at))";
        try (Connection c = Database.connection(); Statement s = c.createStatement()) { s.execute(customers); s.execute(accounts); s.execute(transactions); }
        catch (SQLException e) { throw new DataAccessException("Could not provision schema", e); }
    }
}
