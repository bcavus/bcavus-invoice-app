package com.bcavus.invoiceapp.invoiceservice.repository;

import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomInvoiceRepository extends MongoRepository<Invoice, String> {
    Page<Invoice> findAllByStatus(String status, Pageable pageable);
}