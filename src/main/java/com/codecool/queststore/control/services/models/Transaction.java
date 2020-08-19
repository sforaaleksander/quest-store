package com.codecool.queststore.control.services.models;

import java.util.Date;
import java.util.Optional;

public abstract class Transaction {

    private String name;
    private String description;
    private int cost;
    private String categoryName;
    private Date transactionDate;
    private Optional<Date> transactionAcceptedDate;
    private TransactionObjectType transactionObjectType;

    public String getName() {
        return name;
    }

    public Transaction setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Transaction setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public Transaction setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Transaction setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Transaction setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public Optional<Date> getTransactionAcceptedDate() {
        return transactionAcceptedDate;
    }

    public Transaction setTransactionAcceptedDate(Optional<Date> transactionAcceptedDate) {
        this.transactionAcceptedDate = transactionAcceptedDate;
        return this;
    }

    public TransactionObjectType getTransactionObjectType() {
        return transactionObjectType;
    }

    public Transaction setTransactionObjectType(TransactionObjectType transactionObjectType) {
        this.transactionObjectType = transactionObjectType;
        return this;
    }
}
