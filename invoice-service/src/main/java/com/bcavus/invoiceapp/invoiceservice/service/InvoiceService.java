package com.bcavus.invoiceapp.invoiceservice.service;


import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;

public interface InvoiceService {
    InvoiceDTO createInvoice(CreateInvoiceDTO createInvoiceDTO);
}