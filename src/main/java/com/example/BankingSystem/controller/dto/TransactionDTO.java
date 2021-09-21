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
public class TransactionDTO {
    @NotEmpty(message = "Sender Account id can't be empty or null.")
    @Pattern(regexp = "\\d+", message = "Sender account id must consist of at least one digit and only digits.")
    private String senderAccountId;

    @NotEmpty(message = "Receiver Account id can't be empty or null.")
    @Pattern(regexp = "\\d+", message = "Receiver account id must consist of at least one digit and only digits.")
    private String receiverAccountId;

    @NotEmpty(message = "Receiver Account Owner Name can't be empty or null.")
    private String receiverAccountOwnerName;

    @NotEmpty(message = "Amount can't be empty or null.")
    @Digits(integer = 12, fraction = 2, message = "Only digits allowed for Amount.")
    private String amount;

}
