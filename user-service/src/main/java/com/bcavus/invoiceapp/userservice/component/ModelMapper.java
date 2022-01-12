package com.bcavus.invoiceapp.userservice.component;

import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.dto.request.CreateUserDTO;
import com.bcavus.invoiceapp.userservice.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ModelMapper {

    public UserDTO mapToUserDTO(CreateUserDTO createUserDTO) {
        return UserDTO.builder()
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .email(createUserDTO.getEmail())
                .build();
    }

    public UserDTO mapToUserDTO(User userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .build();
    }
}