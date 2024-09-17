package com.mediasoft.i_collect.communs.configs;

import com.mediasoft.i_collect.models.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return Optional.of(user.getId());
    }
}
