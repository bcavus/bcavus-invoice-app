package com.bcavus.invoiceapp.userservice.api;

import com.bcavus.invoiceapp.userservice.dto.UserDTO;
import com.bcavus.invoiceapp.userservice.dto.request.CreateUserDTO;
import com.bcavus.invoiceapp.userservice.dto.response.APIResponse;
import com.bcavus.invoiceapp.userservice.dto.response.ServiceResponse;
import com.bcavus.invoiceapp.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}