package com.bcavus.invoiceapp.userservice.service;

import com.bcavus.invoiceapp.userservice.component.ModelConverter;
import com.bcavus.invoiceapp.userservice.component.ModelMapper;
import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.dto.request.CreateUserDTO;
import com.bcavus.invoiceapp.userservice.exception.UserAlreadyExistsException;
import com.bcavus.invoiceapp.userservice.model.User;
import com.bcavus.invoiceapp.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ModelConverter modelConverter;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           ModelConverter modelConverter) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUser) {

        final Optional<UserDTO> user = Optional.of(this.modelMapper.mapToUserDTO(createUser));

        this.userRepository.findByEmail(user.get().getEmail())
                .ifPresent(foundUser -> {
                    logger.warn("[UserService/createUser]: User with given email already exists." + foundUser.getEmail());

                    throw new UserAlreadyExistsException("User with given email already exists" + foundUser.getEmail());
                });

        User createdUser = this.userRepository.insert(this.modelConverter.convertToUserModel(user.get()));

        logger.info("[UserService/createUser]: Successfully created a user " + createdUser);

        return this.modelMapper.mapToUserDTO(createdUser);
    }
}