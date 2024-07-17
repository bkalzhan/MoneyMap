package com.example.MoneyMap.service;

import com.example.MoneyMap.model.Category;
import com.example.MoneyMap.model.Transaction;
import com.example.MoneyMap.repo.CategoryRepo;
import com.example.MoneyMap.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public List<Transaction> getAllTransactions(){
        return transactionRepo.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepo.findById(id).orElse(null);
    }

    public List<Transaction> getTransactionsByCategoryId(Long categoryId) {
        return transactionRepo.findTransactionsByCategory(categoryId);
    }

    public Transaction saveTransaction(Transaction transaction){
        Category category = categoryRepo.findById(transaction.getCategory().getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        transaction.setCategory(category);
        return transactionRepo.save(transaction);
    }

    public void deleteTransaction(Long id){
        transactionRepo.deleteById(id);
    }

    public List<Transaction> getTransactionsForDay (LocalDate date, Boolean isIncome){
        return transactionRepo.findTransactionsByDateBetween(date, date.plusDays(1), isIncome);
    }

    public List<Transaction> getTransactionsForWeek (LocalDate date, Boolean isIncome){
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusWeeks(1);
        return transactionRepo.findTransactionsByDateBetween(startOfWeek, endOfWeek, isIncome);
    }

    public List<Transaction> getTransactionsForMonth (LocalDate date, Boolean isIncome){
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = startOfMonth.plusMonths(1);
        return transactionRepo.findTransactionsByDateBetween(startOfMonth, endOfMonth, isIncome);
    }

    public List<Transaction> getTransactionsForPeriod (LocalDate startDate, LocalDate endDate, Boolean isIncome){
        return transactionRepo.findTransactionsByDateBetween(startDate, endDate, isIncome);
    }
}
