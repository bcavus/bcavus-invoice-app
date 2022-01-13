package com.bcavus.invoiceapp.expenseservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Budget {
    private Integer limit;
    private Integer spend;
    private Integer current;
}