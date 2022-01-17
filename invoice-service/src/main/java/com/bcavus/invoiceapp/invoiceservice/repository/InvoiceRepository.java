package com.bcavus.invoiceapp.invoiceservice.repository;

import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String>, CustomInvoiceRepository {
}