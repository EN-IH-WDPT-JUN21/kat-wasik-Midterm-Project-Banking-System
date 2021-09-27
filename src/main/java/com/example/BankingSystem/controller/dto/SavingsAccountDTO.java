package com.example.BankingSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccountDTO {
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

    @Digits(integer = 12, fraction = 2, message = "Only digits allowed for Minimum Balance.")
    @DecimalMax(value = "1000", message = "Maximum value for Minimum Balance is 1000.")
    @DecimalMin(value = "100", message = "Minimum value for Minimum Balance is 100.")
    private String minimumBalance;

    @Digits(integer = 1, fraction = 4, message = "Only digits allowed for Interest Fee.")
    @DecimalMax(value = "0.5", message = "Maximum value for Interest Rate is 0.5")
    @DecimalMin(value  = "0", message = "Minimum value for Interest Rate is 0")
    private String interestRate;


}
