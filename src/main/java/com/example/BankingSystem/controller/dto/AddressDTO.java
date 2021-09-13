package com.example.BankingSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @NotEmpty(message = "Street can't be empty or null.")
    private String street;
    @NotEmpty(message = "City can't be empty or null.")
    private String city;
    @NotEmpty(message = "Postal code can't be empty or null.")
    private String postalCode;
    @NotEmpty(message = "Country can't be empty or null.")
    private String country;
}
