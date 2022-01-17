package com.bcavus.invoiceapp.userservice.service;

import com.bcavus.invoiceapp.userservice.dto.PaginatedUsersDTO;
import com.bcavus.invoiceapp.userservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.dto.request.CreateUserDTO;

public interface UserService {
    UserDTO createUser(CreateUserDTO createUser);
    UserDTO getUserById(String userId);
    UserDTO getUserByEmail(String email);
    PaginatedUsersDTO getAllUsers(PaginationMetadata metadata);

    void validateUserExpense(String invoiceId, String email, Integer expenseAmount);
}