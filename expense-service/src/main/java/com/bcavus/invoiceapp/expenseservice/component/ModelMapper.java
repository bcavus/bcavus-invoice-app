package com.bcavus.invoiceapp.expenseservice.component;

import com.bcavus.invoiceapp.expenseservice.dto.BudgetDTO;
import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.dto.request.CreateExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.model.Budget;
import com.bcavus.invoiceapp.expenseservice.model.Expense;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ModelMapper {

    public ExpenseDTO mapToExpenseDTO(CreateExpenseDTO createExpenseDTO) {
        return ExpenseDTO.builder()
                .userId(createExpenseDTO.getUserId())
                .build();
    }

    public ExpenseDTO mapToExpenseDTO(Expense expenseEntity) {
        return ExpenseDTO.builder()
                .id(expenseEntity.getId())
                .userId(expenseEntity.getUserId())
                .budget(this.mapToBudgetDTO(expenseEntity.getBudget()))
                .build();
    }

    public BudgetDTO mapToBudgetDTO(Budget budgetEntity) {
        return BudgetDTO.builder()
                .limit(budgetEntity.getLimit())
                .spend(budgetEntity.getSpend())
                .remaining(budgetEntity.getRemaining())
                .build();
    }
}