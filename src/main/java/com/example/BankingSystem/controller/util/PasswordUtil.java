package com.example.BankingSystem.controller.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    public static final String encryptedPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        return passwordEncoder.encode(password);
    }

    public static void main(String[] args) {
        System.out.println("Encrypted password:");
        System.out.println(encryptedPassword("w@ckyOwl98"));
    }
}