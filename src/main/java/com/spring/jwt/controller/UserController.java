package com.spring.jwt.controller;

import com.spring.jwt.request.AuthRequest;
import com.spring.jwt.request.UserInfoRequest;
import com.spring.jwt.response.APIResponse;
import com.spring.jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/create/user")
    public ResponseEntity<APIResponse> addNewUser(@RequestBody UserInfoRequest request) {
        return service.addUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        return service.authenticateUser(authRequest);
    }

    @GetMapping("/user/token/test")
    public String testToken() {
        return "Able to access using token";
    }

}