package com.bcavus.invoiceapp.expenseservice.api;

import com.bcavus.invoiceapp.expenseservice.config.ExpenseServiceConfiguration;
import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.dto.ServiceDTO;
import com.bcavus.invoiceapp.expenseservice.dto.request.APIResponse;
import com.bcavus.invoiceapp.expenseservice.dto.request.ServiceResponse;
import com.bcavus.invoiceapp.expenseservice.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path= { "/api/expense" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseAPI {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseAPI.class);

    @Autowired
    private final ExpenseService expenseService;

    public ExpenseAPI(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping(path = {"/"})
    public ResponseEntity<APIResponse> get() {
        return ResponseEntity.ok(APIResponse.builder()
                .code(200)
                .status("OK")
                .success(true)
                .data(ExpenseDTO.builder()
                        .id("1")
                        .userId("1")
                        .build())
                .build()
        );
    }
}