package com.example.BankingSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {
    @NotEmpty(message = "Balance can't be empty or null.")
    @Digits(integer = 12, fraction = 2, message = "Only digits allowed for Balance.")
    private String balance;
}
