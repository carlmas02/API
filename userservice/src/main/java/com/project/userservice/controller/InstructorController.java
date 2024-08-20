package com.project.userservice.controller;

import com.project.userservice.exceptions.InstructorNotFoundException;
import com.project.userservice.exceptions.InvalidInstructorDataException;
import com.project.userservice.model.Instructor;
import com.project.userservice.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service/api/instructors")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public List<Instructor> getAllInstructors() throws Exception {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public Instructor getInstructorById(@PathVariable int id) throws Exception {
        Instructor instructor = instructorService.getInstructorById(id);
        if (instructor == null) {
            throw new InstructorNotFoundException("Instructor not found with ID: " + id);
        }
        return instructor;
    }

    @GetMapping("/email/{email}")
    public Instructor getInstructorByEmailId(@PathVariable String email) throws Exception {
        Instructor instructor = instructorService.getInstructorByEmailId(email);
        if (instructor == null) {
            throw new InstructorNotFoundException("Instructor not found with email: " + email);
        }
        return instructor;
    }

    @GetMapping("/department/{department}")
    public List<Instructor> getInstructorsByDepartment(@PathVariable String department) throws Exception {
        return instructorService.getInstructorsByDepartment(department);
    }

    @PostMapping
    public void createInstructor(@RequestBody Instructor instructor) throws Exception {
        if (instructor == null || instructor.getInstructorId() == null || instructor.getUser() == null) {
            throw new InvalidInstructorDataException("Invalid instructor data provided.");
        }
        instructorService.createInstructor(instructor);
    }

    @PutMapping("/{id}")
    public void updateInstructor(@PathVariable int id, @RequestBody Instructor instructor) throws Exception {
        instructor.setInstructorId(id);
        if (instructorService.getInstructorById(id) == null) {
            throw new InstructorNotFoundException("Instructor not found with ID: " + id);
        }
        instructorService.updateInstructor(instructor);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable int id) throws Exception {
        if (instructorService.getInstructorById(id) == null) {
            throw new InstructorNotFoundException("Instructor not found with ID: " + id);
        }
        instructorService.deleteInstructor(id);
    }
}

/*
 * package com.project.userservice.controller;


import com.project.userservice.model.Instructor;
import com.project.userservice.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service/api/instructors")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public List < Instructor > getAllInstructors()
    		throws Exception {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public Instructor getInstructorById(@PathVariable int id) 
    		throws Exception {
        return instructorService.getInstructorById(id);
    }

    @GetMapping("/email/{email}")
    public Instructor getInstructorByEmailId(@PathVariable String email) 
    		throws Exception {
        return instructorService.getInstructorByEmailId(email);
    }

    @GetMapping("/department/{department}")
    public List < Instructor > getInstructorsByDepartment(@PathVariable String department)
    		throws Exception 
    {
        return instructorService.getInstructorsByDepartment(department);
    }

    @PostMapping
    public void createInstructor(@RequestBody Instructor instructor)
    		throws Exception {
        instructorService.createInstructor(instructor);
    }

    @PutMapping("/{id}")
    public void updateInstructor(@PathVariable int id, @RequestBody Instructor instructor)
    		throws Exception {
        instructor.setInstructorId(id);
        instructorService.updateInstructor(instructor);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable int id)
    		throws Exception {
        instructorService.deleteInstructor(id);
    }
}

*/