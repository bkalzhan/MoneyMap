package com.example.MoneyMap.repo;

import com.example.MoneyMap.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.date >= :startDate AND t.date < :endDate AND t.category.isIncome = :isIncome")
    List<Transaction> findTransactionsByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("isIncome") Boolean isIncome);

    @Query("SELECT t FROM Transaction t WHERE t.category.categoryId = :categoryId")
    List<Transaction> findTransactionsByCategory(@Param("categoryId") Long categoryId);
}
