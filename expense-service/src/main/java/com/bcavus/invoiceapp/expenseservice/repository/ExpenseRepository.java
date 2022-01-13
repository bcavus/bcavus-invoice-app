package com.bcavus.invoiceapp.expenseservice.repository;

import com.bcavus.invoiceapp.expenseservice.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String>, CustomExpenseRepository {
}