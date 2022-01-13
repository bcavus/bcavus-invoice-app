package com.bcavus.invoiceapp.expenseservice.dto;

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
public class ExpenseDTO extends ServiceDTO{

    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "userId", required = true)
    private String userId;

}