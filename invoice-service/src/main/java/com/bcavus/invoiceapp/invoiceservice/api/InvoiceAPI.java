package com.bcavus.invoiceapp.invoiceservice.api;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginatedInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.invoiceservice.dto.request.APIResponse;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.request.ServiceResponse;
import com.bcavus.invoiceapp.invoiceservice.service.InvoiceService;
import com.bcavus.invoiceapp.invoiceservice.service.messaging.InvoiceMessageService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

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

        final InvoiceDTO foundInvoiceDTO = this.invoiceService.getInvoiceById(invoiceId);

        logger.info("[InvoicaAPI/getById]: " + foundInvoiceDTO);

        return new ServiceResponse<InvoiceDTO>().response(foundInvoiceDTO);
    }


    @PostMapping(path = { "/" })
    public ResponseEntity<APIResponse> create (@RequestBody CreateInvoiceDTO createInvoiceDTO) {
        final InvoiceDTO createdInvoiceDTO = this.invoiceService.createInvoice(createInvoiceDTO);

        logger.info("[InvoicaAPI/create]: " + createdInvoiceDTO);

        return new ServiceResponse<InvoiceDTO>().response(createdInvoiceDTO);
    }

    @GetMapping(path = { "/list"})
    public ResponseEntity<APIResponse> list(@RequestParam(value = "page", defaultValue = "0")
                                            @Min(value = 0, message = "Page must be greater or equal to 0")
                                            @NonNull final Integer page,

                                            @RequestParam(value = "size", defaultValue = "1")
                                            @Min(value = 1, message = "Size must be greater or equal to 1")
                                            @NonNull final Integer size) {

        final PaginationMetadata metadata = PaginationMetadata.builder().page(page).size(size).build();

        final PaginatedInvoiceDTO paginatedInvoicesDTO = this.invoiceService.getAllInvoices(metadata);

        logger.info("[InvoiceAPI/list]: " + paginatedInvoicesDTO);

        return new ServiceResponse<PaginatedInvoiceDTO>().response(paginatedInvoicesDTO);
    }
}