package com.kirana.kirana_register.repository.postgres;

import com.kirana.kirana_register.entity.postgres.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
