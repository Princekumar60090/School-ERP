package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.User;
import com.schoolerp.SchoolERP.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "basicAuth")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE USER
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.createUser(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // UPDATE USER DETAILS
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable String id,
            @RequestBody User update) {

        User updated = userService.updateUser(new ObjectId(id), update);
        return ResponseEntity.ok(updated);
    }

    // CHANGE PASSWORD
    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable String id,
            @RequestBody String newPassword) {

        userService.changePassword(new ObjectId(id), newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    // ACTIVATE / DEACTIVATE
    @PutMapping("/{id}/active")
    public ResponseEntity<String> activateDeactivate(
            @PathVariable String id,
            @RequestParam boolean active) {

        userService.setActiveStatus(new ObjectId(id), active);
        return ResponseEntity.ok("User active status updated");
    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET ONE USER
    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(new ObjectId(id)));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(new ObjectId(id));
        return ResponseEntity.ok("User deleted successfully");
    }
}
