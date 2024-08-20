package com.project.userservice.service;

import com.project.userservice.model.Student;
import com.project.userservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List < Student > getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public Student getStudentByEmailId(String email) {
        return studentRepository.findByUserEmail(email);
    }
}