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

        return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername(), java.util.List.of(user.getRole())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        userService.createUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(request.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email not found!");
        }

        User user = userOptional.get();
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setResetOtp(otp);
        user.setResetOtpExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        emailService.sendOtpEmail(user.getEmail(), otp);
        return ResponseEntity.ok("OTP sent to your email!");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(request.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email not found!");
        }

        User user = userOptional.get();
        if (user.getResetOtp() == null || !user.getResetOtp().equals(request.getOtp())) {
            return ResponseEntity.badRequest().body("Error: Invalid OTP!");
        }

        if (user.getResetOtpExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Error: OTP has expired!");
        }

        return ResponseEntity.ok("OTP verified successfully!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(request.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email not found!");
        }

        User user = userOptional.get();
        if (user.getResetOtp() == null || !user.getResetOtp().equals(request.getOtp())) {
            return ResponseEntity.badRequest().body("Error: Invalid OTP!");
        }

        if (user.getResetOtpExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Error: OTP has expired!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetOtp(null);
        user.setResetOtpExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully!");
    }
}
