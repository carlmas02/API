package com.example.Testing_JWT.service;

import com.example.Testing_JWT.domain.UserEntity;
import com.example.Testing_JWT.exception.UserAlreadyExistsException;

public interface UserService {
    public UserEntity addUser(UserEntity userEntity) throws UserAlreadyExistsException;

    public UserEntity checkEmailAndPassword(UserEntity userEntity);
}
