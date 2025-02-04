package org.jasonadriel.web;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.jasonadriel.dto.TransactionDto;
import org.jasonadriel.model.Transaction;
import org.jasonadriel.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String home() {
        return """
                <html lang='en'>
                    <body>
                        <h1>Bank Servlet</h1>
                        <p>Welcome to MyBank!</p>
                    </body>
                </html>
                """;
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam(required = false) String id) {
        List<Transaction> transactions = new ArrayList<>();
        if (id != null) {
            Transaction transaction = transactionService.findById(id);
            if (transaction == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found.");
            } else {
                transactions.add(transaction);
            }
        } else {
            transactions.addAll(transactionService.getAll());
        }

        return transactions;
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.create(transactionDto.getReference(), transactionDto.getAmount());
    }

}
