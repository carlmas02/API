package com.project.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository < User, Integer > {
    User findByEmail(String email);

//    boolean exists(Integer userId);
//    boolean existsById(Integer id);
}