package com.spring.jwt.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class JwtResponse {

    private String type;
    private String token;
    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}