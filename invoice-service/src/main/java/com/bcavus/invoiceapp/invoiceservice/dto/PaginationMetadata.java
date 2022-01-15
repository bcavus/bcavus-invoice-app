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
public class PaginationMetadata extends ServiceDTO{
    @JsonProperty(value = "page", required = true)
    private Integer page;

    @JsonProperty(value = "size", required = true)
    private Integer size;

    @JsonProperty(value = "totalPage", required = true)
    private Integer totalPageNumber;

    @JsonProperty(value = "total", required = true)
    private Long total;
}