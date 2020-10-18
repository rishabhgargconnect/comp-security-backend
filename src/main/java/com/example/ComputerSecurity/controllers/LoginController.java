package com.example.ComputerSecurity.controllers;

import com.example.ComputerSecurity.config.DynamoDBConfig;
import com.example.ComputerSecurity.dto.requestDto.SignUpRequest;
import com.example.ComputerSecurity.dto.responseDto.SignInResponse;
import com.example.ComputerSecurity.dto.responseDto.SignUpResponse;
import com.example.ComputerSecurity.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class LoginController {
    final AuthenticationService authenticationService;

    @PostMapping(value = "sign-in")
    @CrossOrigin(origins = "http://localhost:3000")
    public SignInResponse signIn(@RequestParam String email, @RequestParam String password) throws Exception {
        return authenticationService.isValidUser(email, password);
    }

    @PostMapping(value = "sign-up")
    @CrossOrigin(origins = "http://localhost:3000")
    public SignUpResponse signIUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        return authenticationService.signUp(signUpRequest);
    }
}
