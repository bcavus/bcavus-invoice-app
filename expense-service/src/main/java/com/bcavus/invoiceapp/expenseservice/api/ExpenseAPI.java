package com.bcavus.invoiceapp.expenseservice.api;

import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.dto.request.APIResponse;
import com.bcavus.invoiceapp.expenseservice.dto.request.CreateExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.dto.request.ServiceResponse;
import com.bcavus.invoiceapp.expenseservice.dto.request.SpendExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.service.ExpenseService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= { "/api/expense" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseAPI {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseAPI.class);

    @Autowired
    private final ExpenseService expenseService;

    public ExpenseAPI(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<APIResponse> getById(@PathVariable(value = "id") @NonNull final String expenseId) {
        final ExpenseDTO foundExpenseDTO = this.expenseService.getExpenseById(expenseId);

        logger.info("[ExpenseAPI/getById]: " + expenseId);

        return new ServiceResponse<ExpenseDTO>().response(foundExpenseDTO);
    }

    @GetMapping(path = { "/" })
    public ResponseEntity<APIResponse> getByUserId(@RequestParam(value = "userid") @NonNull final String userId) {
        final ExpenseDTO foundExpenseDTO = this.expenseService.getExpenseByUserId(userId);

        logger.info("[ExpenseAPI/getByUserId]: " + userId);

        return new ServiceResponse<ExpenseDTO>().response(foundExpenseDTO);
    }

    @PostMapping(path = { "/" })
    public ResponseEntity<APIResponse> create(@RequestBody CreateExpenseDTO createExpenseDTO) {
        final ExpenseDTO createdExpenseDTO = this.expenseService.createExpense(createExpenseDTO.getUserId());

        logger.info("[ExpenseAPI/create]: " + createdExpenseDTO);

        return new ServiceResponse<ExpenseDTO>().response(createdExpenseDTO);
    }

    @PutMapping(path = { "/update" })
    public ResponseEntity<APIResponse> update(@RequestBody final SpendExpenseDTO spendExpenseDTO) {
        final ExpenseDTO updatedExpenseDTO = this.expenseService.spendAmountFromExpenseBudgetByUserId(spendExpenseDTO.getUserId(),spendExpenseDTO.getAmount());

        logger.info("[ExpenseAPI/update]: " + updatedExpenseDTO);

        return new ServiceResponse<ExpenseDTO>().response(updatedExpenseDTO);
    }
}