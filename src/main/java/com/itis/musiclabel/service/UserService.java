package com.itis.musiclabel.service;

import com.itis.musiclabel.model.ArtistProfile;
import com.itis.musiclabel.model.LabelProfile;
import com.itis.musiclabel.model.User;
import com.itis.musiclabel.model.User.UserRole;
import com.itis.musiclabel.repository.ArtistProfileRepository;
import com.itis.musiclabel.repository.LabelProfileRepository;
import com.itis.musiclabel.repository.UserRepository;
import com.itis.musiclabel.util.PasswordHasher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ArtistProfileRepository artistProfileRepository;
    private final LabelProfileRepository labelProfileRepository;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository,
                       ArtistProfileRepository artistProfileRepository,
                       LabelProfileRepository labelProfileRepository,
                       PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.artistProfileRepository = artistProfileRepository;
        this.labelProfileRepository = labelProfileRepository;
        this.passwordHasher = passwordHasher;
    }

    public User registerUser(String username, String email, String password, UserRole role) {
        if (userRepository.findByUsername(username).isPresent()
                || userRepository.findByEmail(email).isPresent()) {
            return null;
        }

        String passwordHash = passwordHasher.hashPassword(password);
        User user = new User(username, email, passwordHash, role);
        user = userRepository.save(user);

        if (role == UserRole.ARTIST) {
            ArtistProfile profile = new ArtistProfile(user, username, null, null);
            artistProfileRepository.save(profile);
        } else {
            LabelProfile profile = new LabelProfile(user, username, null, null, null);
            labelProfileRepository.save(profile);
        }

        return user;
    }

    @Transactional(readOnly = true)
    public ArtistProfile getArtistProfile(Long userId) {
        return artistProfileRepository.findByUserId(userId).orElse(null);
    }

    @Transactional(readOnly = true)
    public LabelProfile getLabelProfile(Long userId) {
        return labelProfileRepository.findByUserId(userId).orElse(null);
    }

    public boolean saveArtistProfile(ArtistProfile profile) {
        try {
            artistProfileRepository.save(profile);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveLabelProfile(LabelProfile profile) {
        try {
            labelProfileRepository.save(profile);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateUserProfile(Long userId, String username, String email) {
        Optional<User> userByUsername = userRepository.findByUsername(username);
        if (userByUsername.isPresent() && !userByUsername.get().getId().equals(userId)) {
            throw new RuntimeException("Username already taken");
        }

        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(userId)) {
            throw new RuntimeException("Email already taken");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(username);
        user.setEmail(email);
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
            return userRepository.findByEmail(springUser.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        throw new RuntimeException("Not authenticated");
    }
}