package com.bcavus.invoiceapp.invoiceservice.dto.request;

import com.bcavus.invoiceapp.invoiceservice.dto.ServiceDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ServiceResponse<T extends ServiceDTO> {
    private T data;

    public ResponseEntity<APIResponse> response(T responseDTO){

        final APIResponse response = APIResponse
                .builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .data(responseDTO)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}