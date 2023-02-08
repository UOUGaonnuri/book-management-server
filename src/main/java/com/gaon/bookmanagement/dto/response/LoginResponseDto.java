package com.gaon.bookmanagement.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String username;
    private String accessToken;

    @Builder
    public LoginResponseDto(String username, String accessToken) {
        this.username = username;
        this.accessToken = "Bearer "+accessToken;
    }
}
