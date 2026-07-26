package com.bank.service;

import com.bank.model.BankAccount;
import com.bank.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

public final class BankService {
    private final AccountRepository accounts;
    public BankService(AccountRepository accounts) { this.accounts = accounts; }

    public void deposit(long accountId, BigDecimal amount, String reference) {
        BankAccount account = accounts.find(accountId);
        account.lock().lock();
        try { account.deposit(amount); accounts.updateBalanceAndAudit(account, "DEPOSIT", amount, reference); }
        finally { account.lock().unlock(); }
    }
    public void withdraw(long accountId, BigDecimal amount, String reference) {
        BankAccount account = accounts.find(accountId);
        account.lock().lock();
        try { account.withdraw(amount); accounts.updateBalanceAndAudit(account, "WITHDRAWAL", amount, reference); }
        finally { account.lock().unlock(); }
    }
    /** Locks accounts in stable ID order to avoid deadlocks during competing transfers. */
    public void transfer(long fromId, long toId, BigDecimal amount, String reference) {
        if (fromId == toId) throw new IllegalArgumentException("Transfer accounts must differ");
        BankAccount from = accounts.find(fromId), to = accounts.find(toId);
        BankAccount first = from.id() < to.id() ? from : to, second = first == from ? to : from;
        Lock a = first.lock(), b = second.lock(); a.lock(); b.lock();
        try {
            from.withdraw(amount); to.deposit(amount);
            accounts.updateBalanceAndAudit(from, "TRANSFER_OUT", amount, reference + " -> " + to.accountNumber());
            accounts.updateBalanceAndAudit(to, "TRANSFER_IN", amount, reference + " <- " + from.accountNumber());
        } finally { b.unlock(); a.unlock(); }
    }
}
