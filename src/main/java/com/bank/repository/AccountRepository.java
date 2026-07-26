package com.bank.repository;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.DataAccessException;
import com.bank.model.BankAccount;
import com.bank.model.SavingsAccount;
import java.math.BigDecimal;
import java.sql.*;

public final class AccountRepository {
    public BankAccount find(long id) {
        String sql = "SELECT id, customer_id, account_number, balance FROM accounts WHERE id=?";
        try (Connection c = Database.connection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id); ResultSet r = p.executeQuery();
            if (!r.next()) throw new AccountNotFoundException(id);
            return new SavingsAccount(r.getLong("id"), r.getLong("customer_id"), r.getString("account_number"), r.getBigDecimal("balance"));
        } catch (SQLException e) { throw new DataAccessException("Could not load account", e); }
    }
    public void updateBalanceAndAudit(BankAccount account, String type, BigDecimal amount, String reference) {
        String update = "UPDATE accounts SET balance=? WHERE id=?";
        String audit = "INSERT INTO transactions(account_id, transaction_type, amount, balance_after, reference_note) VALUES(?,?,?,?,?)";
        try (Connection c = Database.connection()) {
            c.setAutoCommit(false);
            try (PreparedStatement u = c.prepareStatement(update); PreparedStatement a = c.prepareStatement(audit)) {
                u.setBigDecimal(1, account.balance()); u.setLong(2, account.id()); u.executeUpdate();
                a.setLong(1, account.id()); a.setString(2, type); a.setBigDecimal(3, amount); a.setBigDecimal(4, account.balance()); a.setString(5, reference); a.executeUpdate();
                c.commit();
            } catch (SQLException e) { c.rollback(); throw e; }
        } catch (SQLException e) { throw new DataAccessException("Could not persist transaction", e); }
    }
}
