package com.bank.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class BankAccountConcurrencyTest {
    @Test
    void preservesBalanceDuringConcurrentDeposits() throws InterruptedException {
        SavingsAccount account = new SavingsAccount(1, 1, "SB-1001", new BigDecimal("100.00"));
        ExecutorService pool = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 100; i++) {
            pool.submit(() -> { account.lock().lock(); try { account.deposit(new BigDecimal("1.00")); } finally { account.lock().unlock(); } });
        }
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        assertEquals(new BigDecimal("200.00"), account.balance());
    }
}
