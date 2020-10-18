package com.example.ComputerSecurity.dto.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class SignUpResponse {

    private String errorMsg;
    private boolean userCreated;
}
