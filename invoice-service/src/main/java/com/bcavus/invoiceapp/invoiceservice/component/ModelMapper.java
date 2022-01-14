package com.bcavus.invoiceapp.invoiceservice.component;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public InvoiceDTO mapToInvoiceDTO(Invoice invoice) {
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .status(invoice.getStatus())
                .firstName(invoice.getFirstName())
                .lastName(invoice.getLastName())
                .email(invoice.getEmail())
                .amount(invoice.getAmount())
                .product(invoice.getProduct())
                .billNumber(invoice.getBillNumber())
                .build();
    }
}