package com.project.userservice.service;

import com.project.userservice.model.Instructor;
import com.project.userservice.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public List < Instructor > getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor getInstructorById(int instructorId) {
        return instructorRepository.findByInstructorId(instructorId);
    }

    public Instructor getInstructorByEmailId(String email) {
        return instructorRepository.findByUserEmail(email);
    }

    public List < Instructor > getInstructorsByDepartment(String department) {
        return instructorRepository.findByDepartment(department);
    }

    public void createInstructor(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    public void updateInstructor(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    public void deleteInstructor(int instructorId) {
        instructorRepository.deleteById(instructorId);
    }
}