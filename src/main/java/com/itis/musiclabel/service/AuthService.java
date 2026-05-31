package com.itis.musiclabel.service;

import com.itis.musiclabel.model.User;
import com.itis.musiclabel.repository.UserRepository;
import com.itis.musiclabel.util.PasswordHasher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
// не используется
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean passwordMatches = passwordHasher.verifyPassword(password, user.getPasswordHash());

            if (passwordMatches) {
                return user;
            }
        }

        return null;
    }
}