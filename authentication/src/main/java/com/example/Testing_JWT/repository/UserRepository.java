package com.example.Testing_JWT.repository;

import com.example.Testing_JWT.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    public UserEntity findByEmailAndPassword(String userName , String password);
    public UserEntity findByEmail(String email);

}

