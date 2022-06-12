package com.player2.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString(onlyExplicitlyIncluded = true)
public class JWTToken {

    public static final byte[] JWT_TOKEN_SECRET = "Tiberiu".getBytes();
    public static final int JWT_ACCESS_TOKEN_LIFETIME = 1000 * 60 * 5; //5 minutes
    public static final int JWT_REFRESH_TOKEN_LIFETIME = 1000 * 60 * 60 * 2; //2 hours
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(JWT_TOKEN_SECRET); //not something you would do in a production "Tiberiu"
    private static final JWTVerifier verifier = JWT.require(ALGORITHM).build();

    private String token;
    @ToString.Include private final String subject;
    @ToString.Include private final Date expirationDate;
    @ToString.Include private final String issuer;
    @ToString.Include private final List<String> authorities;
    @ToString.Include private boolean expired;

    public JWTToken(String token) {
        this.token = token;
        DecodedJWT decodedJWT;
        try{
             decodedJWT = verifier.verify(token);
        } catch (TokenExpiredException e) {
            this.subject = null;
            this.expirationDate = null;
            this.issuer = null;
            this.authorities = null;
            this.expired = true;
            return;
        }

        this.subject = decodedJWT.getSubject();
        this.expirationDate = decodedJWT.getExpiresAt();
        this.issuer = decodedJWT.getIssuer();
        this.authorities = decodedJWT.getClaim("roles").asList(String.class);
        this.expired = false;
    }

    public JWTToken(String subject, String issuer, int lifetime) {
        this.subject = subject;
        this.expirationDate = new Date(System.currentTimeMillis() + lifetime);
        this.issuer = issuer;
        this.authorities = List.of();
        this.expired = false;
        createToken();

    }

    public JWTToken(String subject,
                    String issuer,
                    List<GrantedAuthority> authorities,
                    int lifetime
    ) {

        this.subject = subject;
        this.expirationDate = new Date(System.currentTimeMillis() + lifetime);
        this.issuer = issuer;
        this.authorities = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        this.expired = false;
        createToken();
    }

    public boolean isExpired(){
        return expired;
    }
    public void setExpired(boolean expired) {this.expired = expired;}


    private void createToken(){
        this.token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(expirationDate)
                .withIssuer(issuer)
                .withClaim("roles", authorities)
                .sign(ALGORITHM);
    }
}
