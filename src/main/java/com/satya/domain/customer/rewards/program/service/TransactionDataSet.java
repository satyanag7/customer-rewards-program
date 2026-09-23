package com.satya.domain.customer.rewards.program.service;

import com.satya.domain.customer.rewards.program.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TransactionDataSet {

    private final List<Transaction> transactions = List.of(
        new Transaction(1, 101, "Airpods", LocalDate.of(2026, 6, 15), 120.0),
        new Transaction(2, 102, "Monitor", LocalDate.of(2026, 6, 20), 75.0),
        new Transaction(3, 101, "TV", LocalDate.of(2026, 7, 5), 200.0),
        new Transaction(4, 103, "Speakers", LocalDate.of(2026, 7, 10), 50.0),
        new Transaction(5, 102, "Monitor", LocalDate.of(2026, 7, 25), 150.0),
        new Transaction(6, 101, "Headphones", LocalDate.of(2026, 7, 15), 20.0),
        new Transaction(7, 103, "Keyboard", LocalDate.of(2026, 7, 5), 60.0),
        new Transaction(8, 102, "Mouse", LocalDate.of(2026, 8, 20), 40.0),
        new Transaction(9, 101, "Laptop", LocalDate.of(2026, 8, 10), 300.0),
        new Transaction(10, 103, "Webcam", LocalDate.of(2026, 8, 15), 90.0)
        );

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
