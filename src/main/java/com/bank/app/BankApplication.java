package com.bank.app;

import com.bank.repository.SchemaManager;

/** Application bootstrap: initializes the durable storage schema. */
public final class BankApplication {
    private BankApplication() { }
    public static void main(String[] args) {
        SchemaManager.createTables();
        System.out.println("Bank Management System is ready. MySQL schema verified.");
    }
}
