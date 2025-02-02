package org.jasonadriel.service;

import org.jasonadriel.model.Transaction;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TransactionService {
    List<Transaction> transactions = new CopyOnWriteArrayList<>();

    public TransactionService() {}

    public List<Transaction> getAll() {
        return transactions;
    }

    public Transaction findById(String id) {
        return transactions.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public Transaction create(String reference, Integer amount) {
        Transaction transaction = new Transaction(reference, amount);
        transactions.add(transaction);
        return transaction;
    }
}
