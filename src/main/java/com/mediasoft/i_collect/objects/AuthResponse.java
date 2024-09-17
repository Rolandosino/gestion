package com.mediasoft.i_collect.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediasoft.i_collect.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("message")
    private String message;
    @JsonProperty("user")
    private UserEntity user;

    public AuthResponse(String message) {
        this.message = message;
    }
}
