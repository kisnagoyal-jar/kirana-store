package com.kirana.kirana_register.repository.postgres;

import com.kirana.kirana_register.entity.postgres.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem,String> {
    List<TransactionItem> findByTransactionId(String transactionId);
}
