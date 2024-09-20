package com.mediasoft.gestion_produit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfosDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("emailAdresse")
    private String emailAdresse;

    @JsonProperty("role")
    private String role;
}
