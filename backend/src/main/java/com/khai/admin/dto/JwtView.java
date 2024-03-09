package com.khai.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khai.admin.dto.user.UserView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtView {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("user_details")
    private UserView userDetails;
}
