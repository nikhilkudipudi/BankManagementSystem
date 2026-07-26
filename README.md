# Bank Management System

A Core Java banking backend that models customers, accounts, and auditable transactions. It uses JDBC with MySQL for persistence and locks per account to make concurrent deposits and withdrawals safe.

## Features

- OOP domain model with encapsulation, inheritance, and polymorphism
- Thread-safe deposits, withdrawals, and transfers
- MySQL schema with normalized `customers`, `accounts`, and `transactions` tables
- JDBC repositories and transaction audit trail
- Clear exception types for account, balance, and persistence errors

## Prerequisites

- Java 17+
- MySQL 8+
- Maven 3.9+ 


