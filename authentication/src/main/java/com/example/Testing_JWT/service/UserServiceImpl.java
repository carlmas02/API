package com.example.Testing_JWT.service;

import com.example.Testing_JWT.domain.UserEntity;
import com.example.Testing_JWT.exception.UserAlreadyExistsException;
import com.example.Testing_JWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserEntity addUser(UserEntity userEntity) throws UserAlreadyExistsException {
        UserEntity user = userRepository.findByEmail(userEntity.getEmail());
        if (user == null) {
            String encodePassword = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(encodePassword);
            userRepository.save(userEntity);
        } else {
            throw new UserAlreadyExistsException();
        }
        return userEntity;
    }

    @Override
    public UserEntity checkEmailAndPassword(UserEntity userEntity) {
        UserEntity checkUserEntity = userRepository.findByEmail(userEntity.getEmail());

        if (checkUserEntity == null) {
            return null;
        }

        String dbSavedPassword = checkUserEntity.getPassword();
        String providedPassword = userEntity.getPassword();

        if (passwordEncoder.matches(providedPassword, dbSavedPassword)) {
            return userEntity;
        } else {
            return null;
        }

    }
}
