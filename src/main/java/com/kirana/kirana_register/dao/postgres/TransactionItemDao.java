package com.kirana.kirana_register.dao.postgres;

import com.kirana.kirana_register.entity.postgres.TransactionItem;
import com.kirana.kirana_register.repository.postgres.TransactionItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionItemDao {

    private final TransactionItemRepository transactionItemRepository;

    public TransactionItemDao(TransactionItemRepository transactionItemRepository) {
        this.transactionItemRepository = transactionItemRepository;
    }

    public TransactionItem save(TransactionItem item) {
        return transactionItemRepository.save(item);
    }

    public List<TransactionItem> findByTransactionId(String transactionId) {
        return transactionItemRepository.findByTransactionId(transactionId);
    }
}
