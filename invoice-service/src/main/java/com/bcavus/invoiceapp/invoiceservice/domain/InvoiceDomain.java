package com.bcavus.invoiceapp.invoiceservice.domain;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.invoiceservice.model.InvoiceStatus;
import lombok.NonNull;

public interface InvoiceDomain {
    boolean isPaginationValid(@NonNull PaginationMetadata metadata);
    InvoiceDTO updateInvoiceStatus(@NonNull InvoiceStatus status, InvoiceDTO invoiceDTO);
}