package com.bank.exception;
import java.math.BigDecimal;
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String account, BigDecimal balance, BigDecimal requested) {
        super("Insufficient funds in " + account + ": balance=" + balance + ", requested=" + requested);
    }
}
