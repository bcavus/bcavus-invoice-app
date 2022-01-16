package com.bcavus.invoiceapp.invoiceservice.domain;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginationMetadata;
import lombok.NonNull;

public interface InvoiceDomain {
    boolean isPaginationValid(@NonNull PaginationMetadata metadata);
    InvoiceDTO updateInvoiceStatus(@NonNull boolean isAcceptable, InvoiceDTO invoiceDTO);
}