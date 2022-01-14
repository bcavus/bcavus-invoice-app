package com.bcavus.invoiceapp.expenseservice.domain;

import com.bcavus.invoiceapp.expenseservice.dto.BudgetDTO;
import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ExpenseDomainImpl implements ExpenseDomain {
    @Override
    public boolean hasEnoughBudget(ExpenseDTO expense, Integer amount) {
        BudgetDTO budget = expense.getBudget();

        return (budget.getRemaining() >= amount);
    }

    @Override
    public ExpenseDTO spendAmount(ExpenseDTO expense, Integer amount) {
        BudgetDTO currentBudget = expense.getBudget();

        BudgetDTO updatedBudget = BudgetDTO.builder()
                .limit(currentBudget.getLimit())
                .spend(currentBudget.getSpend() + amount)
                .remaining(currentBudget.getRemaining() - amount)
                .build();

        return ExpenseDTO.builder()
                .id(expense.getId())
                .userId(expense.getUserId())
                .budget(updatedBudget)
                .build();
    }
}