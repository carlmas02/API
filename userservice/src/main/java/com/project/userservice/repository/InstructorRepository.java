package com.project.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.userservice.model.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository < Instructor, Integer > {
    Instructor findByInstructorId(int instructorId); // consider
    Instructor findByUserEmail(String mentorEmail);
    List < Instructor > findByDepartment(String department);
}