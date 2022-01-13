package com.bcavus.invoiceapp.userservice.domain;

import com.bcavus.invoiceapp.userservice.dto.PaginationMetadata;

public interface UserDomain {
    boolean isPaginationValid(PaginationMetadata metadata);
}