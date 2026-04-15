package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.dto.*;
import com.schoolerp.SchoolERP.entity.User;
import com.schoolerp.SchoolERP.repository.UserRepository;
import com.schoolerp.SchoolERP.security.JwtUtils;
import com.schoolerp.SchoolERP.service.EmailService;
import com.schoolerp.SchoolERP.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = userRepository.findByUsername(loginRequest.getUsername()).get();

        return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername(), user.getRole()));
    }

    @PostMapping("/register")
            user.setRole("STUDENT"); // Default role
        }
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
