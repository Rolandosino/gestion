package com.mediasoft.i_collect.controllers;

import com.mediasoft.i_collect.models.UserEntity;
import com.mediasoft.i_collect.objects.AuthResponse;
import com.mediasoft.i_collect.security.utils.AuthentificationDTO;
import com.mediasoft.i_collect.security.utils.AuthentificationService;
import com.mediasoft.i_collect.security.utils.JwtTokenUtil;
import com.mediasoft.i_collect.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@RestController
@Tag(name = "Authentification controller")
@RequestMapping(AuthController.API_ROOT)
public class AuthController {

    /**
     * Auth API root.
     */
    public static final String API_ROOT = "/auth";
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthentificationService authentificationService;


    /**
     * Rest API for to login the user.
     */
    @PostMapping(path = "login")
    @Operation(description = "Login a user.")
    @ApiResponse(responseCode = "200", description = "User is connect, location in the header.")
    public ResponseEntity<AuthResponse> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        try {
            UserEntity user = userService.getUserByEmail(authentificationDTO.email());
            if(!user.isActif()){
                return new ResponseEntity<>(new AuthResponse("La connexion échouée! L'utilisateur n'est pas actif"),
                        HttpStatus.BAD_REQUEST);
            }
            AuthResponse authResponse = authentificationService.authenticate(authentificationDTO);
            authResponse.setMessage("Connexion effectuée avec succès.");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(new AuthResponse("La connexion échouée"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Rest API to logout the user.
     */
    @PostMapping(path = "logout")
    @Operation(description = "Logout user.")
    @ApiResponse(responseCode = "200", description = "User is disconnected, location in the header.")
    public void deconnexion() {
        this.jwtTokenUtil.deconnexion();
    }

    /**
     * Rest API to refresh the token.
     */
    @PostMapping(path = "refresh-token")
    @Operation(description = "Refresh a token.")
    @ApiResponse(responseCode = "200", description = "Token is refreshed, location in the header.")
    public @ResponseBody
    Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return this.jwtTokenUtil.refreshToken(refreshTokenRequest);
    }

}
