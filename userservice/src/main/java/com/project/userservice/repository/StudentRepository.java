package com.project.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.userservice.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudentId(int studentId);
    Student findByUserEmail(String studentMail);
}