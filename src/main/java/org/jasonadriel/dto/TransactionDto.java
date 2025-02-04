package org.jasonadriel.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransactionDto {
    @NotBlank
    @Size(min = 1, max = 64)
    private String reference;

    @NotNull
    @Digits(integer = 32, fraction = 8)
    private BigDecimal amount;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
