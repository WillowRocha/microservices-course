package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    public Page<UserModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void save(UserModel model) {
        repository.save(model);
    }

    @Override
    public void deleteUser(UserModel model) {
        repository.delete(model);
    }
}
