package com.project.userservice.service;

import com.project.userservice.model.Student;
import com.project.userservice.model.User;
import com.project.userservice.model.Instructor;
import com.project.userservice.model.Role;
import com.project.userservice.repository.InstructorRepository;
import com.project.userservice.repository.StudentRepository;
import com.project.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository; // **Added: Inject StudentRepository**
    
    @Autowired
    private InstructorRepository instructorRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Create a new user
    public User createUser(User user) {
        try {
            User savedUser = userRepository.save(user); // **Save the User first**

            // **Check if the role is STUDENT and create a Student entity**
            if (user.getRole() == Role.STUDENT) {
                Student student = new Student();
                student.setUser(savedUser); // Link the saved user
                // Set other properties of the student as needed (e.g., major, totalCredits)
                studentRepository.save(student);
            }

            if (user.getRole() == Role.INSTRUCTOR) {
                Instructor instructor = new Instructor();
                instructor.setUser(savedUser); 
                instructorRepository.save(instructor);
            }

            return savedUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Or handle the exception as needed
        }
    }

    // Update an existing user
    public User updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            return userRepository.save(user);
        }
        return null;
    }

    // Delete a user by ID
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}






/*

package com.project.userservice.service;

import com.project.userservice.model.User;
import com.project.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List < User > getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Integer userId) {
        Optional < User > user = userRepository.findById(userId);
        return user.orElse(null);
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Create a new user
    //    public User createUser(User user) {
    //        return userRepository.save(user);
    //    }

//    public User createUser(User user) {
//        try {
//            // Perform any necessary validation or business logic
//            return userRepository.save(user);
//        } catch (Exception e) {
//            // Log the error for debugging
//            e.printStackTrace();
//            throw e; // Or handle the exception as needed
//        }
//    }
    
    // Create a new user
    public User createUser(User user) {
        try {
            // Perform any necessary validation or business logic
            return userRepository.save(user);
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            throw e; // Or handle the exception as needed
        }
    }



    //    // Update an existing user
    //    public User updateUser(User user) {
    //        if (userRepository.exists(user.getUserId())) {
    //            return userRepository.save(user);
    //        }
    //        return null;
    //    }
    public User updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            return userRepository.save(user);
        }
        return null;
    }


    // Delete a user by ID
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}

*/