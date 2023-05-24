package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @JsonView(UserDto.UserView.RegistrationPost.class) UserDto dto) {
        if (userService.existsByUsername(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username already exists.");
        }
        if (userService.existsByEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email already exists.");
        }

        var user = new UserModel();
        BeanUtils.copyProperties(dto, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setCreationDate(DateUtil.getLocalDateTimeUTC());
        user.setLastUpdateDate(DateUtil.getLocalDateTimeUTC());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
