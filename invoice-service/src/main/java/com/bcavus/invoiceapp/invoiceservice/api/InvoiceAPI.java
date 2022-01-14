package com.bcavus.invoiceapp.invoiceservice.api;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.request.APIResponse;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.request.ServiceResponse;
import com.bcavus.invoiceapp.invoiceservice.service.InvoiceService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = { "/api/invoice" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceAPI {
    private final static Logger logger = LoggerFactory.getLogger(InvoiceAPI.class);

    @Autowired
    private final InvoiceService invoiceService;

    public InvoiceAPI(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping(path = { "/{id}" })
    public ResponseEntity<APIResponse> getById (@PathVariable(value = "id") @NonNull final String invoiceId) {


        return new ServiceResponse<APIResponse>().response(new APIResponse());
    }

    @PostMapping(path = { "/"})
    public ResponseEntity<APIResponse> create (@RequestBody CreateInvoiceDTO createInvoiceDTO) {
        final InvoiceDTO createdInvoiceDTO = this.invoiceService.createInvoice(createInvoiceDTO);

        logger.info("[InvoicaAPI/create]: " + createdInvoiceDTO);

        return new ServiceResponse<InvoiceDTO>().response(createdInvoiceDTO);
    }
}