package com.player2.server.security;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This class holds two JWTToken classes, each for the access token and refresh token
 *
 * @see JWTToken
 */
@Getter
public class JWTAuthenticationToken implements Authentication {

    private final JWTToken accessToken;
    private final JWTToken refreshToken;

    public JWTAuthenticationToken(String accessToken, String refreshToken) {
        this.accessToken = new JWTToken(accessToken);
        this.refreshToken = new JWTToken(refreshToken);
    }

    public JWTAuthenticationToken(JWTToken accessToken, JWTToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return accessToken.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * @return the original token
     */
    @Override
    public Object getCredentials() {
        return accessToken.getToken();
    }

    /**
     * @return all the access token's information (except the token itself)
     */
    @Override
    public Object getDetails() {
        return accessToken.toString();
    }

    /**
     * @return the issuer of the token
     */
    @Override
    public Object getPrincipal() {
        return accessToken.getIssuer();
    }

    /**
     * @return if the access token is expired
     */
    @Override
    public boolean isAuthenticated() {
        return !accessToken.isExpired();
    }

    /**
     * @param isAuthenticated false for immediately expiring the access token
     *                        <br/> true for refreshing the access token if it's expired.
     *                        If the access token not expired, nothing happens.
     * @throws IllegalArgumentException if both refresh and access token are expired and a refresh is attempted
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            if (accessToken.isExpired() && refreshToken.isExpired())
                throw new IllegalArgumentException();

            if (accessToken.isExpired() && !refreshToken.isExpired())
                accessToken.refresh(JWTToken.JWT_ACCESS_TOKEN_LIFETIME);
        } else {
            accessToken.refresh(0);
        }
    }

    /**
     * @return the username
     */
    @Override
    public String getName() {
        return accessToken.getSubject();
    }
}
