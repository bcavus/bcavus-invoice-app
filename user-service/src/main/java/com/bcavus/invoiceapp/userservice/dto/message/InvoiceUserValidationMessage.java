package com.bcavus.invoiceapp.userservice.dto.message;

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
public class InvoiceUserValidationMessage extends RabbitMessage {

    @JsonProperty(value = "invoiceid", required = true)
    private String invoiceId;

    @JsonProperty(value = "email", required = true)
    private String userEmail;

    @JsonProperty(value = "amount", required = true)
    private Integer expenseAmount;

    @JsonProperty(value = "valid", required = true)
    private boolean valid;

}