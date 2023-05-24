package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") UUID id) {
        Optional<UserModel> optUser = service.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.ok(optUser.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") UUID id) {
        Optional<UserModel> optUser = service.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        service.deleteUser(optUser.get());
        return ResponseEntity.ok("User successfully deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") UUID id, @RequestBody @JsonView(UserDto.UserView.UserPut.class) UserDto dto) {
        Optional<UserModel> optUser = service.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var user = optUser.get();
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setCpf(dto.getCpf());
        user.refreshLastUpdateDate();
        service.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable("id") UUID id, @RequestBody @JsonView(UserDto.UserView.PasswordPut.class) UserDto dto) {
        Optional<UserModel> optUser = service.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var user = optUser.get();
        if (!user.getPassword().equals(dto.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old password do not match.");
        }
        user.setPassword(dto.getPassword());
        user.refreshLastUpdateDate();
        service.save(user);
        return ResponseEntity.ok("Password successfully updated.");
    }

    @PutMapping("/{id}/imageUrl")
    public ResponseEntity<Object> updateImage(@PathVariable("id") UUID id, @RequestBody @JsonView(UserDto.UserView.ImagePut.class) UserDto dto) {
        Optional<UserModel> optUser = service.findById(id);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var user = optUser.get();
        user.setImageUrl(dto.getImageUrl());
        user.refreshLastUpdateDate();
        service.save(user);
        return ResponseEntity.ok(user);
    }
}
