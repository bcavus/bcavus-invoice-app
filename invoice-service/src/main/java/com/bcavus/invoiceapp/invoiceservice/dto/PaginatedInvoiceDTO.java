package com.bcavus.invoiceapp.invoiceservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedInvoiceDTO extends ServiceDTO{
    @JsonProperty(value = "metadata", required = true)
    private PaginationMetadata paginationMetadata;

    @JsonProperty(value = "invoices", required = true)
    private List<InvoiceDTO> invoices;
}