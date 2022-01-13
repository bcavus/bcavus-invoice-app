package com.bcavus.invoiceapp.userservice.domain;

import com.bcavus.invoiceapp.userservice.dto.PaginationMetadata;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserDomainImpl implements UserDomain{
    @Override
    public boolean isPaginationValid(@NonNull PaginationMetadata metadata) {
        return metadata.getPage() >= 0 && metadata.getSize() >= 1;
    }
}