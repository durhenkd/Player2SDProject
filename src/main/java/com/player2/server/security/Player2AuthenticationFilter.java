package com.player2.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class Player2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManagerImpl authenticationManager;

    @Autowired
    public Player2AuthenticationFilter(AuthenticationManagerImpl authenticationManager) {
        super("/**");
        setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response )
            throws AuthenticationException
    {

        log.info("Authentication request, type: " + request.getAuthType());

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length >= 2){
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("access_token"))
                    .findFirst();
            Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("refresh_token"))
                    .findFirst();

            if(accessTokenCookie.isPresent() && refreshTokenCookie.isPresent()){
                return this.authenticationManager.authenticate(
                        new JWTAuthenticationToken(
                           new JWTToken(accessTokenCookie.get().getValue()),
                           new JWTToken(refreshTokenCookie.get().getValue())
                        ));
            } //if (accessTokenCookie.isPresent() && refreshTokenCookie.isPresent())
        } //if (cookies != null && cookies.length >= 2)


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Trying to log in: " + username + " pass: " + password);
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            return this.authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    )  {

        log.info("Successful authentication");

        if (authResult instanceof JWTAuthenticationToken token){
            response.addCookie(new Cookie("access_token", token.getAccessToken().getToken()));
            response.addCookie(new Cookie("refresh_token", token.getRefreshToken().getToken()));
            return;
        }

        User user = (User) authResult.getPrincipal();
        JWTToken access_token = new JWTToken(
                user.getUsername(),
                request.getRequestURL().toString(),
                user.getAuthorities().stream().toList(),
                JWTToken.JWT_ACCESS_TOKEN_LIFETIME
        );

        JWTToken refresh_token = new JWTToken(
                user.getUsername(),
                request.getRequestURL().toString(),
                JWTToken.JWT_REFRESH_TOKEN_LIFETIME
        );

//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); //hardcoded and necessary
//        response.setHeader("Access-Control-Allow-Headers", "content-type"); //hardcoded and necessary
//        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,POST"); //hardcoded and necessary
//        response.setHeader("Access-Control-Allow-Credentials", "true"); //hardcoded and necessary
//        response.setHeader("Access-Control-Max-Age", "1800"); //hardcoded and necessary

        response.addCookie(new Cookie("access_token", access_token.toString()));
        response.addCookie(new Cookie("refresh_token", refresh_token.toString()));
    }


}

