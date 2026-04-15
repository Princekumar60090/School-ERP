package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.User;
import com.schoolerp.SchoolERP.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATE USER
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // UPDATE USER DETAILS
    public User updateUser(ObjectId id, User update) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (update.getUsername() != null)
            existing.setUsername(update.getUsername());

        if (update.getRole() != null)
            existing.setRole(update.getRole());

        if (update.getPassword() != null && !update.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(update.getPassword()));
        }

        return userRepository.save(existing);
    }

    // CHANGE PASSWORD
    public void changePassword(ObjectId id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // ACTIVATE/DEACTIVATE USER
    public void setActiveStatus(ObjectId id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(active);
        userRepository.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET ONE USER
    public User getUserById(ObjectId id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // DELETE USER
    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }
}
