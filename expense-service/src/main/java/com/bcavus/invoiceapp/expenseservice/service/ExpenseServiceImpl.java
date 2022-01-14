package com.bcavus.invoiceapp.expenseservice.service;

import com.bcavus.invoiceapp.expenseservice.component.ModelConverter;
import com.bcavus.invoiceapp.expenseservice.component.ModelMapper;
import com.bcavus.invoiceapp.expenseservice.config.ExpenseServiceConfiguration;
import com.bcavus.invoiceapp.expenseservice.dto.BudgetDTO;
import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.exception.ExpenseAlreadyExistsException;
import com.bcavus.invoiceapp.expenseservice.exception.NoExpenseFound;
import com.bcavus.invoiceapp.expenseservice.model.Expense;
import com.bcavus.invoiceapp.expenseservice.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private final ExpenseRepository expenseRepository;

    @Autowired
    private final ExpenseServiceConfiguration expenseServiceConfiguration;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ModelConverter modelConverter;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              ExpenseServiceConfiguration expenseServiceConfiguration,
                              ModelMapper modelMapper,
                              ModelConverter modelConverter) {
        this.expenseRepository = expenseRepository;
        this.expenseServiceConfiguration = expenseServiceConfiguration;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @Override
    public ExpenseDTO createExpense(String userId) {

        if(userId.isEmpty()) {
            logger.warn("[ExpenseService/createExpense]: userId cannot be null or empty");

            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        this.expenseRepository.findExpenseByUserId(userId)
                .ifPresent(expense -> {
                    logger.warn("[ExpenseService/createExpense]: Cannot create expense with already defined user: " + userId );

                    throw new ExpenseAlreadyExistsException("Cannot create expense with already defined user.");
                });
        
        ExpenseDTO newExpenseDTO = ExpenseDTO.builder()
                .userId(userId)
                .budget(BudgetDTO.builder()
                        .limit(Integer.valueOf(this.expenseServiceConfiguration.getLimit()))
                        .spend(0)
                        .remaining(Integer.valueOf(this.expenseServiceConfiguration.getLimit()))
                        .build())
                .build();

        Expense expense = this.expenseRepository.insert(this.modelConverter.convertToExpense(newExpenseDTO));

        logger.info("[ExpenseService/createExpense]: Successfully created expense: " + expense);

        return this.modelMapper.mapToExpenseDTO(expense);
    }

    @Override
    public ExpenseDTO getExpenseById(String expenseId) {

        if(expenseId.isEmpty()) {
            logger.warn("[ExpenseService/getExpenseById]: expenseId cannot be null or empty");

            throw new IllegalArgumentException("expenseId cannot be null or empty");
        }

        Optional<Expense> foundExpense = this.expenseRepository.findById(expenseId);

        if(foundExpense.isEmpty()) {
            logger.warn("[ExpenseService/getExpenseById]: Cannot find any expense with given id: " + expenseId);

            throw new NoExpenseFound("Cannot find any expense with given expenseId: " + expenseId);
        }

        logger.info("[ExpenseService/getExpenseById]: Successfully retrieved expense: " + foundExpense.get());

        return this.modelMapper.mapToExpenseDTO(foundExpense.get());
    }

    @Override
    public ExpenseDTO getExpenseByUserId(String userId) {

        if(userId.isEmpty()) {
            logger.warn("[ExpenseService/getExpenseByUserId]: userId cannot be null or empty");

            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        Optional<Expense> foundExpense = this.expenseRepository.findExpenseByUserId(userId);

        if(foundExpense.isEmpty()) {
            logger.warn("[ExpenseService/getExpenseByUserId]: Cannot find any expense with given userId: " + userId);

            throw new NoExpenseFound("Cannot find any expense with given userId: " + userId);
        }

        logger.info("[ExpenseService/getExpenseByUserId]: Successfully retrieved expense: " + foundExpense.get());

        return this.modelMapper.mapToExpenseDTO(foundExpense.get());
    }
}