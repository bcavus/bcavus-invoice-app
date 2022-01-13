package com.bcavus.invoiceapp.expenseservice.repository;

import com.bcavus.invoiceapp.expenseservice.model.Expense;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomExpenseRepository extends CrudRepository<Expense, String> {
    Optional<Expense> findExpenseByUserId(String userId);
}