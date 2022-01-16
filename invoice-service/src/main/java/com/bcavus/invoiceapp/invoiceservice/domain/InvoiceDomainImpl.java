package com.bcavus.invoiceapp.invoiceservice.domain;

import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.invoiceservice.model.InvoiceStatus;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDomainImpl implements InvoiceDomain{

    @Override
    public boolean isPaginationValid(@NonNull PaginationMetadata metadata) {
        return metadata.getPage() >= 0 && metadata.getSize() >= 1;
    }

    @Override
    public InvoiceDTO updateInvoiceStatus(@NonNull boolean isAcceptable, InvoiceDTO invoiceDTO) {
        InvoiceDTO invoiceToBeUpdated = invoiceDTO;

        invoiceToBeUpdated = (isAcceptable)
                ? invoiceToBeUpdated.setStatus(InvoiceStatus.ACCEPTED.name())
                : invoiceToBeUpdated.setStatus(InvoiceStatus.REJECTED.name());

        return invoiceToBeUpdated;
    }
}