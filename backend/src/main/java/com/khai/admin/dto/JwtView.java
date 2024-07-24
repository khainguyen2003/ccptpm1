package com.khai.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khai.admin.dto.user.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtView {
    private String accessToken;
    private String refreshToken;
    private UserProfileDto userDetails;

    public JwtView(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
