package com.bcavus.invoiceapp.expenseservice.domain;

import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;

public interface ExpenseDomain {
    boolean hasEnoughBudget(ExpenseDTO expense, Integer amount);

    ExpenseDTO spendAmount(ExpenseDTO expense, Integer amount);
}