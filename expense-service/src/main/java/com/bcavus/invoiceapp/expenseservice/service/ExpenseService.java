package com.bcavus.invoiceapp.expenseservice.service;

import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;

public interface ExpenseService {
    ExpenseDTO createExpense(String userId);
}