package org.jasonadriel.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Transaction {
    private String id;
    private String reference;
    private Integer amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mmZ")
    private ZonedDateTime timestamp;

    public Transaction() {}

    public Transaction(String reference, Integer amount) {
        this.id = UUID.randomUUID().toString();
        this.reference = reference;
        this.amount = amount;
        this.timestamp = ZonedDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
