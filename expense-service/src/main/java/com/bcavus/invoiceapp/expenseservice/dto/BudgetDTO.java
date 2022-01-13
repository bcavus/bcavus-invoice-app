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
public class BudgetDTO extends ServiceDTO{

    @JsonProperty(value = "limit", required = true)
    private Integer limit;

    @JsonProperty(value = "spend", required = true)
    private Integer spend;

    @JsonProperty(value = "remaining", required = true)
    private Integer remaining;

}