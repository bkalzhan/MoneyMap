package com.example.MoneyMap.controller;

import com.example.MoneyMap.model.Transaction;
import com.example.MoneyMap.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<Transaction>> getAllCategories() {
        try {
            List<Transaction> transactionList = transactionService.getAllTransactions();

            if(transactionList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(transactionList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTransactionById/{id}")
    public ResponseEntity<Transaction> getCategoryById(@PathVariable Long id){
        Optional<Transaction> transactionData = Optional.ofNullable(transactionService.getTransactionById(id));
        return transactionData.map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction transactionObj = transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(transactionObj, HttpStatus.OK);
    }

    @PostMapping("/updateTransactionById/{id}")
    public ResponseEntity<Transaction> updateCategory(@PathVariable Long id,
                                                   @RequestBody Transaction newTransactionData) {
        Optional<Transaction> oldTransactionData = Optional.ofNullable(transactionService.getTransactionById(id));
        if(oldTransactionData.isPresent()){
            Transaction updatedTransactionData = oldTransactionData.get();
            updatedTransactionData.setName(newTransactionData.getName());
            updatedTransactionData.setCategory((newTransactionData.getCategory()));
            updatedTransactionData.setAmount(newTransactionData.getAmount());

            Transaction transactionObj = transactionService.saveTransaction(updatedTransactionData);
            return new ResponseEntity<>(transactionObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteTransactionById/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
