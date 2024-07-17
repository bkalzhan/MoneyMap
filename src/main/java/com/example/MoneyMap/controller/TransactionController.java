package com.example.MoneyMap.controller;

import com.example.MoneyMap.model.Transaction;
import com.example.MoneyMap.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
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
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id){
        Optional<Transaction> transactionData = Optional.ofNullable(transactionService.getTransactionById(id));
        return transactionData.map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getTransactionsByCategoryId/{categoryId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategoryId(@PathVariable Long categoryId){
        Optional<List<Transaction>> transactionList = Optional.ofNullable(transactionService.getTransactionsByCategoryId(categoryId));
        if(transactionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return transactionList.map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction transactionObj = transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(transactionObj, HttpStatus.OK);
    }

    @PostMapping("/updateTransactionById/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
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
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/income/day")
    public ResponseEntity<List<Transaction>> getIncomeForDay() {
        LocalDate localDate = LocalDate.now();
        List<Transaction> income = transactionService.getTransactionsForDay(localDate, true);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @GetMapping("/outcome/day")
    public ResponseEntity<List<Transaction>> getOutcomeForDay() {
        LocalDate localDate = LocalDate.now();
        List<Transaction> expense = transactionService.getTransactionsForDay(localDate, false);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @GetMapping("/income/week")
    public ResponseEntity<List<Transaction>> getIncomeForWeek() {
        LocalDate localDate = LocalDate.now();
        List<Transaction> income = transactionService.getTransactionsForWeek(localDate, true);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @GetMapping("/outcome/week")
    public ResponseEntity<List<Transaction>> getOutcomeForWeek() {
        LocalDate localDate = LocalDate.now();
        List<Transaction> expense = transactionService.getTransactionsForWeek(localDate, false);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @GetMapping("/income/month")
    public ResponseEntity<List<Transaction>> getIncomeForMonth() {
        LocalDate localDate = LocalDate.now();
        List<Transaction> income = transactionService.getTransactionsForMonth(localDate, true);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @GetMapping("/outcome/month")
    public ResponseEntity<List<Transaction>> getOutcomeForMonth() {
        LocalDate localDate = LocalDate.now();
        List<Transaction> expense = transactionService.getTransactionsForMonth(localDate, false);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @GetMapping("/outcome/{startDate}&{endDate}")
    public ResponseEntity<List<Transaction>> getOutcomeForPeriod(@PathVariable String startDate, @PathVariable String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            List<Transaction> outcomeList = transactionService.getTransactionsForPeriod(start, end, false);
            if(outcomeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(outcomeList, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/income/{startDate}&{endDate}")
    public ResponseEntity<List<Transaction>> getIncomeForPeriod(@PathVariable String startDate, @PathVariable String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            List<Transaction> incomeList = transactionService.getTransactionsForPeriod(start, end, true);
            if(incomeList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(incomeList, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
