package com.example.ComputerSecurity.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class User {
    private String id;
    private String email;
    private String name;
    private String password;
    private String mobile;
    private boolean primeUser;
}
