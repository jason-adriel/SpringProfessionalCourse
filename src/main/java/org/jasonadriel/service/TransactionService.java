package org.jasonadriel.service;

import org.jasonadriel.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TransactionService {
    private final String bankSlogan;
    List<Transaction> transactions = new CopyOnWriteArrayList<>();

    public TransactionService(@Value("${bank.slogan}") String bankSlogan) {
        this.bankSlogan = bankSlogan;
    }

    public List<Transaction> getAll() {
        return transactions;
    }

    public Transaction findById(String id) {
        return transactions.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public Transaction create(String reference, BigDecimal amount) {
        Transaction transaction = new Transaction(reference, this.bankSlogan, amount, ZonedDateTime.now());
        transactions.add(transaction);
        return transaction;
    }
}
