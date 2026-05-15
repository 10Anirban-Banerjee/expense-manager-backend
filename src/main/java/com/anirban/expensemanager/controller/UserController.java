package com.anirban.expensemanager.controller;

import com.anirban.expensemanager.dto.UserRequestDto;
import com.anirban.expensemanager.entity.User;
import com.anirban.expensemanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.anirban.expensemanager.dto.LoginRequestDto;
import com.anirban.expensemanager.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.anirban.expensemanager.dto.LoginResponseDto;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        String accessToken =
                jwtUtil.generateToken(dto.getEmail());

        String refreshToken =
                jwtUtil.generateRefreshToken(dto.getEmail());

        return new LoginResponseDto(
                accessToken,
                refreshToken
        );
    }

    @PostMapping("/register")
    public User registerUser(
            @Valid @RequestBody UserRequestDto dto) {

        return userService.registerUser(dto);
    }

    @PostMapping("/refresh")
    public String refreshToken(
            @RequestHeader("Authorization")
            String authHeader) {

        String refreshToken =
                authHeader.substring(7);

        String email =
                jwtUtil.extractEmail(refreshToken);

        return jwtUtil.generateToken(email);
    }
}