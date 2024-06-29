package com.spring.jwt.service;

import com.spring.jwt.model.UserInfo;
import com.spring.jwt.request.AuthRequest;
import com.spring.jwt.request.UserInfoRequest;
import com.spring.jwt.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface UserInfoService {
    ResponseEntity<APIResponse> addUser(UserInfoRequest request);
    ResponseEntity<APIResponse> authenticateUser(AuthRequest request);
}
