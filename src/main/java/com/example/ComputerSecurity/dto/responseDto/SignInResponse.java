package com.example.ComputerSecurity.dto.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SignInResponse {
    private boolean isValidUser;
    private String errorMsg;
    private String name;
}
