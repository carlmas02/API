/*
 * package com.project.userservice.controller;
 

import com.project.userservice.model.User;
import com.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.userservice.model.Role;
import java.util.List;

// this is the file
@RestController
@RequestMapping("/user-service/api/users")
public class UserController {
  @Autowired private UserService userService;

  @GetMapping
  public List<User> getAllUsers() throws Exception {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Integer id) throws Exception {
    User user = userService.getUserById(id);
    if (user != null) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws Exception {
    User user = userService.getUserByEmail(email);
    if (user != null) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/check-email/{email}")
  public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
    System.out.println(email);
    User exists = userService.getUserByEmail(email);
    if (exists != null) {
      return new ResponseEntity<>(true, HttpStatus.OK);
    }
    return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    System.out.println(user);

    System.out.println(user.getRole());
    Role role;
    try {
      role = Role.valueOf(user.getRole().toString());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null); // Handle invalid enum value
    }
    user.setRole(role);
    User createdUser = userService.createUser(user);

    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }

  //    @PostMapping
  //    public ResponseEntity<User> createUser(@RequestBody User user)
  //    		throws Exception {
  //        try {
  //            User createdUser = userService.createUser(user);
  //            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  //        } catch (Exception e) {
  //            // Log the error for debugging
  //            e.printStackTrace();
  //            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  //        }
  //    }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails)
      throws Exception {
    // Set the ID from the path variable
    userDetails.setUserId(id);

    // Attempt to update the user
    User updatedUser = userService.updateUser(userDetails);

    if (updatedUser != null) {
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //    @PutMapping("/{id}")
  //    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User
  // userDetails) {
  //        User updatedUser = userService.updateUser(id, userDetails);
  //        if (updatedUser != null) {
  //            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  //        } else {
  //            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  //        }
  //    }
  
  *     // Update an existing user
//     public User updateUser(User user) {
//         if (userRepository.exists(user.getUserId())) {
//             return userRepository.save(user);
//         }
//         return null;
//     }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws Exception {
    if (userService.getUserById(id) != null) {
      userService.deleteUser(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      //            	 new ResourceNotFoundException("User not found with ID: " + id);
    }
  }
}

*/

/*
package com.project.userservice.controller;

import com.project.userservice.exceptions.UserNotFoundException;
import com.project.userservice.exceptions.InvalidRoleException;
import com.project.userservice.model.User;
import com.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.userservice.model.Role;
import java.util.List;

@RestController
@RequestMapping("/user-service/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) throws Exception {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws Exception {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        Role role;
//        try {
//            role = Role.valueOf(user.getRole().toString());
//        } catch (IllegalArgumentException e) {
//            throw new InvalidRoleException("Invalid role provided: " + user.getRole());
//        }
//        user.setRole(role);
//        User createdUser = userService.createUser(user);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Check if the email already exists
        if (userService.getUserByEmail(user.getEmail()) != null) {
            // Return a string message
            return new ResponseEntity<>("Email already exists.", HttpStatus.CONFLICT);
        }

        Role role;
        try {
            role = Role.valueOf(user.getRole().toString());
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException("Invalid role provided: " + user.getRole());
        }
        user.setRole(role);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) throws Exception {
        userDetails.setUserId(id);
        User updatedUser = userService.updateUser(userDetails);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws Exception {
        if (userService.getUserById(id) != null) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }
}

*/

package com.project.userservice.controller;

import com.project.userservice.exceptions.UserNotFoundException;
import com.project.userservice.exceptions.InvalidRoleException;
import com.project.userservice.model.User;
import com.project.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.userservice.model.Role;
import java.util.List;

@RestController
@RequestMapping("/user-service/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) throws Exception {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws Exception {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Check if the email already exists
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email already exists.", HttpStatus.CONFLICT);
        }

        Role role;
        try {
            role = Role.valueOf(user.getRole().toString());
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException("Invalid role provided: " + user.getRole());
        }
        user.setRole(role);
        User createdUser = userService.createUser(user); // **Call the modified createUser method**
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) throws Exception {
        userDetails.setUserId(id);
        User updatedUser = userService.updateUser(userDetails);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws Exception {
        if (userService.getUserById(id) != null) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }
}