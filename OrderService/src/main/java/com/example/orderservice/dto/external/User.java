package com.example.orderservice.dto.external;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance;
}
