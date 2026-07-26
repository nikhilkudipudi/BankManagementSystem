package com.bank.model;

import java.util.Objects;

public final class Customer {
    private final long id;
    private final String fullName;
    private final String email;

    public Customer(long id, String fullName, String email) {
        this.id = id;
        this.fullName = Objects.requireNonNull(fullName, "fullName");
        this.email = Objects.requireNonNull(email, "email");
    }
    public long id() { return id; }
    public String fullName() { return fullName; }
    public String email() { return email; }
}
