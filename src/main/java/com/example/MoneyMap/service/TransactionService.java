package com.example.MoneyMap.service;

import com.example.MoneyMap.model.Category;
import com.example.MoneyMap.model.Transaction;
import com.example.MoneyMap.repo.CategoryRepo;
import com.example.MoneyMap.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Transaction transaction = transactionRepo.findById(id).orElse(null);
        return transaction;
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
}
