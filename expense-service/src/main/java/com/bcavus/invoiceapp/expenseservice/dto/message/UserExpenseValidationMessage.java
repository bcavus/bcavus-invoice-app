package com.bcavus.invoiceapp.expenseservice.dto.message;

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
public class UserExpenseValidationMessage extends RabbitMessage {
    @JsonProperty(value = "invoiceid", required = true)
    private String invoiceId;

    @JsonProperty(value = "userid", required = true)
    private String userId;

    @JsonProperty(value = "amount", required = true)
    private Integer expenseAmount;
}