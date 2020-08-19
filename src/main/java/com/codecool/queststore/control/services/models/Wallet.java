package com.codecool.queststore.control.services.models;

import java.util.Iterator;
import java.util.List;

public class Wallet {

    private List<Transaction> transactions;

    public Wallet(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Iterator<Transaction> getTransactionsIterator() {
        return transactions.iterator();
    }

}
