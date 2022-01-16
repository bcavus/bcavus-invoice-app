package com.bcavus.invoiceapp.userservice.service;

import com.bcavus.invoiceapp.userservice.component.ModelConverter;
import com.bcavus.invoiceapp.userservice.component.ModelMapper;
import com.bcavus.invoiceapp.userservice.domain.UserDomain;
import com.bcavus.invoiceapp.userservice.dto.PaginatedUsersDTO;
import com.bcavus.invoiceapp.userservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.dto.request.CreateUserDTO;
import com.bcavus.invoiceapp.userservice.exception.NoSuchUserFound;
import com.bcavus.invoiceapp.userservice.exception.UserAlreadyExistsException;
import com.bcavus.invoiceapp.userservice.model.User;
import com.bcavus.invoiceapp.userservice.repository.UserRepository;
import com.bcavus.invoiceapp.userservice.service.messaging.message.UserExpenseCreationMessage;
import com.bcavus.invoiceapp.userservice.service.messaging.producer.UserMessageProducerService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDomain userDomain;

    @Autowired
    private final UserMessageProducerService<UserExpenseCreationMessage> userMessageProducerService;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ModelConverter modelConverter;

    public UserServiceImpl(UserRepository userRepository,
                           UserDomain userDomain,
                           UserMessageProducerService<UserExpenseCreationMessage> userMessageProducerService,
                           ModelMapper modelMapper,
                           ModelConverter modelConverter) {
        this.userRepository = userRepository;
        this.userDomain = userDomain;
        this.userMessageProducerService = userMessageProducerService;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUser) {

        final Optional<UserDTO> user = Optional.of(this.modelMapper.mapToUserDTO(createUser));

        this.userRepository.findByEmail(user.get().getEmail())
                .ifPresent(foundUser -> {
                    logger.warn("[UserService/createUser]: User with given email already exists." + foundUser.getEmail());

                    throw new UserAlreadyExistsException("User with given email already exists " + foundUser.getEmail());
                });

        User createdUser = this.userRepository.insert(this.modelConverter.convertToUserModel(user.get()));

        this.userMessageProducerService.sendMessage(UserExpenseCreationMessage.builder().userid(createdUser.getId()).build());

        logger.info("[UserService/createUser]: Successfully created a user " + createdUser);

        return this.modelMapper.mapToUserDTO(createdUser);
    }

    @Override
    public UserDTO getUserById(String userId) {

        if(userId.isEmpty()) {
            logger.warn("[UserService/getUserById]: userId cannot be empty or null");

            throw new IllegalArgumentException("Empty or null string provided.Please provide correct id");
        }

        Optional<User> foundUser;

        foundUser = this.userRepository.findById(userId);

        if(foundUser.isEmpty()) {
            logger.warn("[UserService/getUserById]: Cannot find user with given id:  " + userId);

            throw new NoSuchUserFound("Cannot find user with given id: " + userId);
        }

        logger.info("[UserService/getUserById]: Successfully retrieved a user: " + foundUser);

        return this.modelMapper.mapToUserDTO(foundUser.get());
    }

    @Override
    public UserDTO getUserByEmail(String email) {

        if(email.isEmpty()) {
            logger.warn("[UserService/getUserByEmail]: email cannot be empty or null");

            throw new IllegalArgumentException("Empty or null string provided.Please provide correct email");
        }

        Optional<User> foundUser;

        foundUser = this.userRepository.findByEmail(email);

        if(foundUser.isEmpty()) {
            logger.warn("[UserService/getUserByEmail]: Cannot find user with given email: " + email);

            throw new NoSuchUserFound("Cannot find user with given email: " + email);
        }

        logger.info("[UserService/getUserById]: Successfully retrieved a user " + foundUser);

        return this.modelMapper.mapToUserDTO(foundUser.get());
    }

    @Override
    public PaginatedUsersDTO getAllUsers(@NonNull PaginationMetadata metadata) {

        if(!userDomain.isPaginationValid(metadata)) {
            logger.warn("[UserService/getAllUsers]: Invalid metadata provided: " + metadata);

            throw new IllegalArgumentException("Invalid metadata provided.");
        }

        final Pageable pageable = PageRequest.of(metadata.getPage(), metadata.getSize());

        final Page<User> pagedUsers = this.userRepository.findAll(pageable);

        logger.info("[UserService/getAllUsers]: Successfully retrieved list of users with given metadata");

        return PaginatedUsersDTO.builder()
                .users(pagedUsers.stream().map(this.modelMapper::mapToUserDTO).collect(Collectors.toList()))
                .paginationMetadata(PaginationMetadata.builder()
                        .page(metadata.getPage())
                        .size(metadata.getSize())
                        .totalPageNumber(pagedUsers.getTotalPages())
                        .total(pagedUsers.getTotalElements())
                        .build())
                .build();
    }
}