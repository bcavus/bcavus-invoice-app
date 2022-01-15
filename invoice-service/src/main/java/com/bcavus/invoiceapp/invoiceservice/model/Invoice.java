package com.bcavus.invoiceapp.invoiceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection= "invoices")
public class Invoice {
    @Id
    @NonNull
    private String id;

    @Field
    private InvoiceStatus status;

    @Field
    private String firstName;

    @Field
    private String lastName;

    @Field
    private String email;

    @Field
    private Integer amount;

    @Field
    private String product;

    @Field
    private String billNumber;
}