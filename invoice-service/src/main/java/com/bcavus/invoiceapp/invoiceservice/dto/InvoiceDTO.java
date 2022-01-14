package com.bcavus.invoiceapp.invoiceservice.dto;


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
public class InvoiceDTO extends ServiceDTO{

    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "status", required = true)
    private String status;

    @JsonProperty(value = "firstname", required = true)
    private String firstName;

    @JsonProperty(value = "lastname", required = true)
    private String lastName;

    @JsonProperty(value = "email", required = true)
    private String email;

    @JsonProperty(value = "amount", required = true)
    private Integer amount;

    @JsonProperty(value = "product", required = true)
    private String product;

    @JsonProperty(value = "billno", required = true)
    private String billNumber;

}