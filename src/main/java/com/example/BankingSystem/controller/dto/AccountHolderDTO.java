package com.example.BankingSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolderDTO {
    @NotEmpty(message = "Name can't be empty or null.")
    private String name;

    @NotEmpty(message = "Username can't be empty or null.")
    private String username;

    @NotEmpty(message = "Password can't be empty or null.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password must contain at least one uppercase letter, at least one lowercase letter, at least one digit, at least one special characters and must consist of at least 8 characters.")
    private String password;

    @NotEmpty(message = "Date of birth can't empty or null")
    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "Expected date format: YYYY-MM-DD")
    private String dateOfBirth;

    @NotEmpty(message = "Primary address id can't be empty or null.")
    @Pattern(regexp = "\\d+", message = "Primary address id must consist of at least one digit and only digits.")
    private String primaryAddressId;

    @Pattern(regexp = "\\d+", message = "Secondary address id must consist of at least one digit and only digits.")
    private String mailingAddressId; // optional

    public AccountHolderDTO(String name, String username, String password, String dateOfBirth, String primaryAddressId) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddressId = primaryAddressId;
    }
}