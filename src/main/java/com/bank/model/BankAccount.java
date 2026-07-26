package com.bank.model;

import com.bank.exception.InsufficientFundsException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.ReentrantLock;

/** Base class that encapsulates state and guards balance changes with an account lock. */
public abstract class BankAccount {
    private final long id;
    private final long customerId;
    private final String accountNumber;
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock(true);

    protected BankAccount(long id, long customerId, String accountNumber, BigDecimal openingBalance) {
        this.id = id; this.customerId = customerId; this.accountNumber = accountNumber;
        this.balance = money(openingBalance);
    }
    public long id() { return id; }
    public long customerId() { return customerId; }
    public String accountNumber() { return accountNumber; }
    public synchronized BigDecimal balance() { return balance; }
    public void deposit(BigDecimal amount) { change(money(amount)); }
    public void withdraw(BigDecimal amount) { change(money(amount).negate()); }
    protected synchronized void change(BigDecimal delta) {
        BigDecimal next = balance.add(delta);
        if (next.signum() < 0) throw new InsufficientFundsException(accountNumber, balance, delta.abs());
        balance = next;
    }
    public ReentrantLock lock() { return lock; }
    protected static BigDecimal money(BigDecimal value) {
        if (value == null || value.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }
    public abstract String accountType();
}
