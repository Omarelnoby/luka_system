package com.luka.system.controller;

import com.luka.system.model.User;
import com.luka.system.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> request
    ) {

        String username = request.get("username");

        String password = request.get("password");

        User user = authService.authenticate(
                username,
                password
        );

        if (user == null) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid username or password");
        }

        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);

        response.put("username",
                user.getUsername());

        response.put("role",
                user.getRole().name());

        return ResponseEntity.ok(response);
    }
}