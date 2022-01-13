package com.bcavus.invoiceapp.userservice.api;

import com.bcavus.invoiceapp.userservice.dto.PaginatedUsersDTO;
import com.bcavus.invoiceapp.userservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.dto.request.CreateUserDTO;
import com.bcavus.invoiceapp.userservice.dto.response.APIResponse;
import com.bcavus.invoiceapp.userservice.dto.response.ServiceResponse;
import com.bcavus.invoiceapp.userservice.service.UserService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping(path= { "/api/user" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAPI {
    private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);

    @Autowired
    private final UserService userService;

    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = { "/" })
    public ResponseEntity<APIResponse> create(@RequestBody final CreateUserDTO createUserDTO) {

        final UserDTO createdUserDTO = this.userService.createUser(createUserDTO);

        logger.info("[UserAPI/create]: " + createUserDTO);

        return new ServiceResponse<UserDTO>().response(createdUserDTO);
    }

    @GetMapping(path = { "/{id}" })
    public ResponseEntity<APIResponse> getById(@PathVariable(value = "id" ) @NonNull final String userId) {

        final UserDTO foundUserDTO = this.userService.getUserById(userId);

        logger.info("[UserAPI/getById]: " + foundUserDTO);

        return new ServiceResponse<UserDTO>().response(foundUserDTO);
    }

    @GetMapping(path = { "/{email}" })
    public ResponseEntity<APIResponse> getByEmail(@PathVariable(value = "email" ) @NonNull final String userEmail) {

        final UserDTO foundUserDTO = this.userService.getUserByEmail(userEmail);

        logger.info("[UserAPI/getByEmail]: " + foundUserDTO);

        return new ServiceResponse<UserDTO>().response(foundUserDTO);
    }

    @GetMapping(path = { "/list "})
    public ResponseEntity<APIResponse> list(@RequestParam(value = "page", defaultValue = "0")
                                                @Min(value = 0, message = "Page must be greater or equal to 0")
                                                @NonNull final Integer page,

                                            @RequestParam(value = "size", defaultValue = "1")
                                                @Min(value = 1, message = "Size must be greater or equal to 1")
                                                @NonNull final Integer size) {

        final PaginationMetadata metadata = PaginationMetadata.builder().page(page).size(size).build();

        final PaginatedUsersDTO paginatedUsersDTO = this.userService.getAllUsers(metadata);

        logger.info("[UserAPI/list]: " + paginatedUsersDTO);

        return new ServiceResponse<PaginatedUsersDTO>().response(paginatedUsersDTO);
    }
}