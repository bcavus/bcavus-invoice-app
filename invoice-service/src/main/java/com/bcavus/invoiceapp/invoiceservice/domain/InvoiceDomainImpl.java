package com.bcavus.invoiceapp.invoiceservice.domain;

import com.bcavus.invoiceapp.invoiceservice.dto.PaginationMetadata;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDomainImpl implements InvoiceDomain{
    @Override
    public boolean isPaginationValid(@NonNull PaginationMetadata metadata) {
        return metadata.getPage() >= 0 && metadata.getSize() >= 1;
    }
}