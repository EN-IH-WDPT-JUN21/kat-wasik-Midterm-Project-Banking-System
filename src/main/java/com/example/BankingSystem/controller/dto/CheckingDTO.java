package com.example.BankingSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckingDTO {
    @NotEmpty(message = "Balance can't be empty or null.")
    @Digits(integer = 12, fraction = 2, message = "Only digits allowed for Balance.")
    private String balance;

    @NotEmpty(message = "Secret key can't be empty or null.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Secret key must contain at least one uppercase letter, at least one lowercase letter, at least one digit, at least one special characters and must consist of at least 8 characters.")
    private String secretKey;

    @NotEmpty(message = "Primary Owner id can't be empty or null.")
    @Pattern(regexp = "\\d+", message = "Account Holder id must consist of at least one digit and only digits.")
    private String primaryOwnerId;

    @Pattern(regexp = "\\d+", message = "Secondary Owner id must consist of at least one digit and only digits.")
    private String secondaryOwnerId;
}
