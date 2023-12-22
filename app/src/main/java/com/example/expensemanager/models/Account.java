package com.example.expensemanager.models;

public class Account {
    int accountAmount;
    String name;

    public Account(int accountAmount, String name) {
        this.accountAmount = accountAmount;
        this.name = name;
    }

    public int getAccountAmount() {
        return accountAmount;
    }

    public String getName() {
        return name;
    }
}
