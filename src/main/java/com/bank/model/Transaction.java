package com.bank.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Transaction(long id, long accountId, TransactionType type, BigDecimal amount,
                          BigDecimal balanceAfter, String reference, Instant createdAt) { }
