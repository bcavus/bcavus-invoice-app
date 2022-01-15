package com.bcavus.invoiceapp.invoiceservice.dto.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceExpenseValidationMessage extends InvoiceMessage {

    @JsonProperty(value = "invoiceid", required = true)
    private String invoiceId;

    @JsonProperty(value = "available", required = true)
    private boolean available;

}