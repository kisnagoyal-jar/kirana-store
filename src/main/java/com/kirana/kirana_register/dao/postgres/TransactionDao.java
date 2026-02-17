package com.kirana.kirana_register.dao.postgres;

import com.kirana.kirana_register.entity.postgres.Transaction;
import com.kirana.kirana_register.repository.postgres.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionDao {

    private final TransactionRepository transactionRepository;

    public TransactionDao(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> findById(String originalTransactionId) {
        return transactionRepository.findById(originalTransactionId);
    }
}
