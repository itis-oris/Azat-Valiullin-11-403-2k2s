package com.itis.musiclabel.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {

    public String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean verifyPassword(String plainPassword, String storedHash) {
        if (storedHash == null || plainPassword == null || storedHash.trim().isEmpty()) {
            return false;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), storedHash);
        return result.verified;
    }
}