package com.project.userservice.controller;

import com.project.userservice.exceptions.StudentNotFoundException;
import com.project.userservice.exceptions.InvalidStudentDataException;
import com.project.userservice.model.Student;
import com.project.userservice.model.User;
import com.project.userservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import com.project.userservice.service.UserService;


import java.util.List;

@RestController
@RequestMapping("/user-service/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() throws Exception {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) throws Exception {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        return student;
    }

//    @PostMapping
//    public void createStudent(@RequestBody Student student) throws Exception {
//        if (student == null || student.getStudentId() == null || student.getUser() == null) {
//            throw new InvalidStudentDataException("Invalid student data provided.");
//        }
//        studentService.createStudent(student);
//    }
    
    @PostMapping
    public void createStudent(@RequestBody Student student) throws Exception {
        if (student == null || student.getUser() == null) {
            throw new InvalidStudentDataException("Invalid student data provided.");
        }

        // Save the user first
        User user = student.getUser();
        if (user != null) {
            // Assuming userService has a method to save User
//            UserService.createUser(user); // Save the user
//        	UserService.createUser(User);
        	throw new StudentNotFoundException("Need to create User ");
        	
        }

        // Now save the student
        studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable int id, @RequestBody Student student) throws Exception {
        student.setStudentId(id);
        if (studentService.getStudentById(id) == null) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) throws Exception {
        if (studentService.getStudentById(id) == null) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        studentService.deleteStudent(id);
    }

    @GetMapping("/email/{email}")
    public Student getStudentByEmailId(@PathVariable String email) throws Exception {
        Student student = studentService.getStudentByEmailId(email);
        if (student == null) {
            throw new StudentNotFoundException("Student not found with email: " + email);
        }
        return student;
    }
}


/*
 package com.project.userservice.controller;
 

import com.project.userservice.model.Student;
import com.project.userservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List < Student > getAllStudents()
    		throws Exception {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id)
    		throws Exception {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public void createStudent(@RequestBody Student student)
    		throws Exception {
        studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable int id, @RequestBody Student student) 
    		throws Exception {
        student.setStudentId(id);
        studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id)
    		throws Exception {
        studentService.deleteStudent(id);
    }

    @GetMapping("/email/{email}")
    public Student getStudentByEmailId(@PathVariable String email)
    		throws Exception {
        return studentService.getStudentByEmailId(email);
    }
}
*/