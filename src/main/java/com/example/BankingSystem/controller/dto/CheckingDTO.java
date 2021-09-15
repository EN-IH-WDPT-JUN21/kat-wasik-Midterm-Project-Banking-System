package com.example.BankingSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckingDTO {
    @Digits(integer = 12, fraction = 2, message = "Only digits allowed for Balance.")
    private String balance;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Secret key must contain at least one uppercase letter, at least one lowercase letter, at least one digit, at least one special characters and must consist of at least 8 characters.")
    private String secretKey;

    @Pattern(regexp = "\\d+", message = "Account Holder id must consist of at least one digit and only digits.")
    private String accountHolderId;

    @Pattern(regexp = "\\d+", message = "Secondary Holder id must consist of at least one digit and only digits.")
    private String secondaryHolderId;
}
