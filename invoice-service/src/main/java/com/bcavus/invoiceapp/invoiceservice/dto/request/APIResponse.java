package com.bcavus.invoiceapp.invoiceservice.dto.request;


import com.bcavus.invoiceapp.invoiceservice.dto.ServiceDTO;
import com.bcavus.invoiceapp.invoiceservice.exception.ServiceException;
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
public class APIResponse extends ServiceDTO {

    @JsonProperty(value = "code", required = true)
    private Integer code;

    @JsonProperty(value = "status", required = true)
    private String status;

    @JsonProperty(value = "data", required = true)
    private ServiceDTO data;

    @JsonProperty(value = "exception", required = true)
    private ServiceException exception;

    @JsonProperty(value = "success", required = true)
    private boolean success;
}