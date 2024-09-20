package com.mediasoft.gestion_produit.security.utils;

import com.mediasoft.gestion_produit.models.Jwt;
import com.mediasoft.gestion_produit.models.RefreshToken;
import com.mediasoft.gestion_produit.models.UserEntity;
import com.mediasoft.gestion_produit.repositories.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
public class JwtTokenUtil {


    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    private final JwtRepository jwtRepository;
    public static final String REFRESH = "refresh";
    public static final String TOKEN_INVALIDE = "Token invalide";

    @Value("71d84e93ceaa5d394b8e743b1ca34b125a68124acfadaa8ebc52c860a78798c9")
    private String encryption_key;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public JwtTokenUtil(JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValeurAndDesactiveAndExpire(
                value,
                false,
                false
        ).orElseThrow(() -> new RuntimeException("Token invalide ou inconnu"));
    }

    private void disableTokens(UserEntity utilisateur) {
        final List<Jwt> jwtList = this.jwtRepository.findUtilisateur(utilisateur.getEmailAddress()).peek(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
    }

    public Map<String, String> generateJwt(UserEntity user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + jwtExpiration;

        final Map<String, Object> claims = Map.of(
                "username", user.getUsername(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, user.getEmailAddress()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmailAddress())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    public String extractEmail(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        Date date = this.getClaim(token, Claims::getExpiration);
        System.out.println("date = " + date);
        return date;
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(encryption_key);
        return Keys.hmacShaKeyFor(decoder);
    }

    public void deconnexion() {
        UserEntity utilisateur = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findUtilisateurValidToken(
                false,
                false,
                utilisateur.getEmailAddress()

        ).orElseThrow(() -> new RuntimeException(TOKEN_INVALIDE));
        jwt.setExpire(true);
        jwt.setDesactive(true);
        this.jwtRepository.save(jwt);
    }

    //    @Scheduled(cron = "0 */1 * * * *")
    @Scheduled(cron = "@daily")
    public void removeUselessJwt() {
        log.info("Suppression des token à {}", Instant.now());
        this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
    }

    public RefreshToken getRefreshToken(UserDetails userDetails) {
        return RefreshToken.builder()
                .valeur(generateRefreshToken(userDetails))
                .expire(false)
                .creation(Instant.now())
                .expiration(Instant.now().plusMillis(refreshExpiration))
                .build();
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {
        final Jwt jwt = this.jwtRepository.findByRefreshToken(refreshTokenRequest.get(REFRESH)).orElseThrow(() -> new RuntimeException(TOKEN_INVALIDE));
        if (jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
            throw new RuntimeException(TOKEN_INVALIDE);
        }
        this.disableTokens(jwt.getUser());
        return this.generateJwt(jwt.getUser());
    }
}

