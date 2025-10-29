package com.example.bankservice.dto.external;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    public String getFullName() {
        return firstName + " " + lastName;
    }

}
