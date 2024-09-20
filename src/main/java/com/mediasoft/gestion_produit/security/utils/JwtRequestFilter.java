package com.mediasoft.gestion_produit.security.utils;

import com.mediasoft.gestion_produit.models.Jwt;
import com.mediasoft.gestion_produit.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;
        boolean isTokenExpired = true;
        Jwt bdToken = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            logger.warn("JWT AUTH OKAY");
            jwtToken = authorization.substring(7);
            bdToken = this.jwtTokenUtil.tokenByValue(jwtToken);
            isTokenExpired = jwtTokenUtil.isTokenExpired(jwtToken);
            email = jwtTokenUtil.extractEmail(jwtToken);
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (!isTokenExpired
                && bdToken.getUser().getEmailAddress().equals(email)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.getUserByEmail(email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        chain.doFilter(request, response);
    }

}
