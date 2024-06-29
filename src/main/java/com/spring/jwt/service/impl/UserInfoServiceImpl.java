package com.spring.jwt.service.impl;

import com.spring.jwt.exception.DuplicateUsernameException;
import com.spring.jwt.model.UserInfo;
import com.spring.jwt.repository.UserInfoRepository;
import com.spring.jwt.request.AuthRequest;
import com.spring.jwt.request.UserInfoRequest;
import com.spring.jwt.response.APIResponse;
import com.spring.jwt.response.JwtResponse;
import com.spring.jwt.response.UserInfoResponse;
import com.spring.jwt.security.JwtService;
import com.spring.jwt.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<APIResponse> addUser(UserInfoRequest request) {
        if (repository.findByName(request.getName()).isPresent()) {
           throw new DuplicateUsernameException("Username already used.");
        }
        UserInfo userInfo = repository.save(modelMapper(request));
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(0)
                        .errorMessage("Success")
                        .data(responseMapper(userInfo))
                        .build()
        );
    }

    @Override
    public ResponseEntity<APIResponse> authenticateUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        Optional<UserInfo> userInfo = repository.findByName(authRequest.getUsername());
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("invalid user request !");
        }
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(0)
                        .errorMessage("success")
                        .data(jwtResponseMapper(userInfo.get(), jwtService.generateToken(authRequest.getUsername())))
                        .build()
        );
    }

    private JwtResponse jwtResponseMapper(UserInfo userInfo, String token) {
        return JwtResponse.builder()
                .type("Bearer")
                .token(token)
                .id(userInfo.getId())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .createdDate(userInfo.getCreatedDate())
                .updatedDate(userInfo.getUpdatedDate())
                .build();
    }

    private UserInfo modelMapper(UserInfoRequest request) {
        return UserInfo.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
    }

    private UserInfoResponse responseMapper(UserInfo model) {
        return UserInfoResponse.builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .createdDate(model.getCreatedDate())
                .updatedDate(model.getUpdatedDate())
                .build();
    }

}
