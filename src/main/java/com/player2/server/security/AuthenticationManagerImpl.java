package com.player2.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JWTTokenAuthenticationProvider jwtTokenAuthenticationProvider;

    @Autowired
    public AuthenticationManagerImpl(DaoAuthenticationProvider authenticationProvider,
                                     JWTTokenAuthenticationProvider jwtTokenAuthenticationProvider) {
        this.daoAuthenticationProvider = authenticationProvider;
        this.jwtTokenAuthenticationProvider = jwtTokenAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (jwtTokenAuthenticationProvider.supports(authentication.getClass()))
            return jwtTokenAuthenticationProvider.authenticate(authentication);

        if (daoAuthenticationProvider.supports(authentication.getClass()))
            return daoAuthenticationProvider.authenticate(authentication);

        throw new ProviderNotFoundException("Missing provider for " + authentication.getClass());
    }
}
