package com.bcavus.invoiceapp.userservice.dto.request;

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
public class CreateUserDTO {

    @JsonProperty(value = "firstName", required = true)
    private String firstName;

    @JsonProperty(value = "lastName", required = true)
    private String lastName;

    @JsonProperty(value = "email", required = true)
    private String email;
}