package com.bank.model;

import java.math.BigDecimal;

public final class SavingsAccount extends BankAccount {
    public SavingsAccount(long id, long customerId, String number, BigDecimal balance) { super(id, customerId, number, balance); }
    @Override public String accountType() { return "SAVINGS"; }
}
