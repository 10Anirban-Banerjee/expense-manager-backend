package com.anirban.expensemanager.service;

import com.anirban.expensemanager.dto.UserRequestDto;
import com.anirban.expensemanager.entity.User;
import com.anirban.expensemanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRequestDto dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        String encodedPassword =
                passwordEncoder.encode(dto.getPassword());

        System.out.println(encodedPassword);

        user.setPassword(encodedPassword);

        user.setRole(dto.getRole());

        return userRepository.save(user);
    }
}