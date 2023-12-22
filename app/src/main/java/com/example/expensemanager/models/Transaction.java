package com.example.expensemanager.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Transaction extends RealmObject {

    @PrimaryKey
    @Required
    private String id;
    private String transactionType,category,account;
    private int amount;
    private Date dateSelect;
    private int icon;


    // Default, no-argument constructor required by Realm
    public Transaction() {
    }


    public Transaction(String transactionType, String category, String account, int amount, Date dateSelect, int icon) {
        this.id = generateUniqueId();
        this.transactionType = transactionType;
        this.category = category;
        this.account = account;
        this.amount = amount;
        this.dateSelect = dateSelect;
        this.icon = icon;
    }

    // Method to generate a unique ID
    private String generateUniqueId() {
        return java.util.UUID.randomUUID().toString();
    }
    public String getTransactionType() {
        return transactionType;
    }

    public String getCategory() {
        return category;
    }

    public String getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDateSelect() {
        return dateSelect;
    }

    public int getIcon() {
        return icon;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDateSelect(Date dateSelect) {
        this.dateSelect = dateSelect;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
