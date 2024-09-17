package com.mediasoft.i_collect.security.utils;

import com.mediasoft.i_collect.dto.UserInfosDto;
import com.mediasoft.i_collect.models.*;
import com.mediasoft.i_collect.objects.AuthResponse;
import com.mediasoft.i_collect.repositories.JwtRepository;
import com.mediasoft.i_collect.repositories.UserRepository;
import com.mediasoft.i_collect.services.RoleService;
import com.mediasoft.i_collect.services.ValidationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final JwtRepository jwtRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ValidationService validationService;


    public AuthResponse authenticate(AuthentificationDTO authentificationDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authentificationDTO.email(),
                        authentificationDTO.password()));
        var user = userRepository.findByEmail(authentificationDTO.email())
                .orElseThrow();
        var jwtToken = jwtTokenUtil.generateJwt(user);
        var refreshToken = jwtTokenUtil.getRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken.get("bearer"), refreshToken);

        return AuthResponse.builder()
                .accessToken(jwtToken.get("bearer"))
                .refreshToken(refreshToken.getValeur())
                .user(user)
                .build();
    }

    @Transactional
    public AuthResponse registrerUser(UserInfosDto userInfosDto) throws MessagingException {
        Role role = roleService.getRoleByType(userInfosDto.getRole());


        var userEntity = UserEntity.builder()
                .username(userInfosDto.getUsername())
                .emailAddress(userInfosDto.getEmailAdresse())
                .password(passwordEncoder.encode(userInfosDto.getPassword()))
                .role(role)
                .actif(false)
                .build();
        userEntity = userRepository.save(userEntity);
        //save token for user
        Map<String, String> token = jwtTokenUtil.generateJwt(userEntity);
        var refreshToken = jwtTokenUtil.getRefreshToken(userEntity);
        this.saveUserToken(userEntity, token.get("bearer"), refreshToken);
        this.validationService.enregistrer(userEntity);
        return AuthResponse.builder()
                .accessToken(token.get("bearer"))
                .refreshToken(refreshToken.getValeur())
                .user(userEntity)
                .build();
    }

    public void saveUserToken(UserEntity user, String jwtToken, RefreshToken refreshToken) {
        var token = Jwt.builder()
                .user(user)
                .refreshToken(refreshToken)
                .valeur(jwtToken)
                .expire(false)
                .desactive(false)
                .build();
        jwtRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = jwtRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpire(true);
            token.setDesactive(true);
        });
        jwtRepository.saveAll(validUserTokens);
    }
}
