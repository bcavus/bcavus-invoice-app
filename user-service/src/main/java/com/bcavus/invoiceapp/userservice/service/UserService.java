package com.bcavus.invoiceapp.userservice.service;

import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.dto.request.CreateUserDTO;

public interface UserService {
    UserDTO createUser(CreateUserDTO createUser);
}