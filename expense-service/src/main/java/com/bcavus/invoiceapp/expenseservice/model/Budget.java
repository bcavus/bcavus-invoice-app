package com.bcavus.invoiceapp.expenseservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Budget {

    @Field
    private Integer limit;

    @Field
    private Integer spend;

    @Field
    private Integer remaining;
}