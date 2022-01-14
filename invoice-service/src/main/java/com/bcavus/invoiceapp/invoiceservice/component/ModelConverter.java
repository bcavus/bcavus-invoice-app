package com.bcavus.invoiceapp.invoiceservice.component;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    public Invoice convertToInvoice(CreateInvoiceDTO createInvoiceDTO) {
        return new Invoice()
                .setStatus("PROCESSING")
                .setFirstName(createInvoiceDTO.getFirstName())
                .setLastName(createInvoiceDTO.getLastName())
                .setEmail(createInvoiceDTO.getEmail())
                .setAmount(createInvoiceDTO.getAmount())
                .setProduct(createInvoiceDTO.getProduct())
                .setBillNumber(createInvoiceDTO.getBillNumber());
    }
}