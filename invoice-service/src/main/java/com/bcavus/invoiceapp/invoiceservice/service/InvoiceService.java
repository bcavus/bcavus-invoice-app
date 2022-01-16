package com.bcavus.invoiceapp.invoiceservice.service;


import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginatedInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;
import lombok.NonNull;

public interface InvoiceService {
    InvoiceDTO createInvoice(CreateInvoiceDTO createInvoiceDTO);
    InvoiceDTO getInvoiceById(@NonNull String invoiceId);
    PaginatedInvoiceDTO getAllInvoices(@NonNull PaginationMetadata metadata);

    InvoiceDTO updateInvoiceStatusById(@NonNull String id, @NonNull boolean isAvailable);
}