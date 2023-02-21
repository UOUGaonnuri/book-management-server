package com.gaon.bookmanagement.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String username;
    private String accessToken;
    private String role;

    @Builder
    public LoginResponseDto(String username, String accessToken, String role) {
        this.username = username;
        this.accessToken = "Bearer "+accessToken;
        this.role = role;
    }
}
