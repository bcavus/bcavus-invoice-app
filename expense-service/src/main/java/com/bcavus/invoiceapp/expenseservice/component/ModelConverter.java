package com.bcavus.invoiceapp.expenseservice.component;

import com.bcavus.invoiceapp.expenseservice.dto.BudgetDTO;
import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.model.Budget;
import com.bcavus.invoiceapp.expenseservice.model.Expense;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ModelConverter {

    public Expense convertToExpense(ExpenseDTO expenseDTO) {
        return new Expense()
                .setUserId(expenseDTO.getUserId())
                .setBudget(this.convertToBudget(expenseDTO.getBudget()));
    }

    public Budget convertToBudget(BudgetDTO budgetDTO) {
        return new Budget()
                .setLimit(budgetDTO.getLimit())
                .setSpend(budgetDTO.getSpend())
                .setRemaining(budgetDTO.getRemaining());
    }
}