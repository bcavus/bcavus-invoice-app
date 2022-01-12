package com.bcavus.invoiceapp.userservice.repository;

import com.bcavus.invoiceapp.userservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomUserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
}