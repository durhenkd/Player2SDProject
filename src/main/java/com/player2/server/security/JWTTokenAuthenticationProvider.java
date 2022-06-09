package com.player2.server.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * AuthenticationProvider implementation for JTWAuthenticationToken
 *
 * @see JWTAuthenticationToken
 */
@Component
public class JWTTokenAuthenticationProvider implements AuthenticationProvider {

    /**
     * This authentication method is fundamentally different as it just verifies if the token is expired.<br/>
     * If both the access token and refresh token are expired it throws a runtime exception
     * @return a non-expired JWTAuthenticationToken
     * @throws AuthenticationException if the authentication argument class is not supported
     * @see JWTAuthenticationToken
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!this.supports(authentication.getClass()))
            throw new AuthenticationServiceException(this.getClass().getSimpleName() + " does not support " + authentication.getClass());

        JWTAuthenticationToken token = (JWTAuthenticationToken) authentication;

        if(token.isAuthenticated())
            return token;

        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
