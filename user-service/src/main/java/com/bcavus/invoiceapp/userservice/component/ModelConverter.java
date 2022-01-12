package com.bcavus.invoiceapp.userservice.component;

import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ModelConverter {

    public User convertToUserModel(UserDTO userDTO){
        return new User()
                .setFirstName(userDTO.getFirstName())
                .setLastName(userDTO.getLastName())
                .setEmail(userDTO.getEmail());
    }
}