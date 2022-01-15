package com.bcavus.invoiceapp.invoiceservice.component;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import com.bcavus.invoiceapp.invoiceservice.model.InvoiceStatus;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    public Invoice convertToInvoice(CreateInvoiceDTO createInvoiceDTO) {
        return new Invoice()
                .setStatus(InvoiceStatus.CREATED)
                .setFirstName(createInvoiceDTO.getFirstName())
                .setLastName(createInvoiceDTO.getLastName())
                .setEmail(createInvoiceDTO.getEmail())
                .setAmount(createInvoiceDTO.getAmount())
                .setProduct(createInvoiceDTO.getProduct())
                .setBillNumber(createInvoiceDTO.getBillNumber());
    }

    public Invoice convertToInvoice(InvoiceDTO invoiceDTO) {
        return new Invoice()
                .setId(invoiceDTO.getId())
                .setStatus(InvoiceStatus.fromValue(invoiceDTO.getStatus()))
                .setFirstName(invoiceDTO.getFirstName())
                .setLastName(invoiceDTO.getLastName())
                .setEmail(invoiceDTO.getEmail())
                .setAmount(invoiceDTO.getAmount())
                .setProduct(invoiceDTO.getProduct())
                .setBillNumber(invoiceDTO.getBillNumber());
    }
}