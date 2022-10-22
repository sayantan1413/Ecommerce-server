package com.application.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String companyName;
    private final String email;
    private final String password;
    private final String phoneNo;
    private final long gstNo;
    private final String Address;
    private final Boolean manufacturer;

}
