package com.player2.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class Player2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final OrRequestMatcher request =
            new OrRequestMatcher(
                    new RegexRequestMatcher("/api/player(/[^/]+)*", "POST"),
                    new RegexRequestMatcher("/api/player(/[^/]+)*", "GET"),
                    new RegexRequestMatcher("/api/clique(/[^/]+)*", "POST"),
                    new RegexRequestMatcher("/api/clique(/[^/]+)*", "GET"),
                    new RegexRequestMatcher("/api/login.*", "POST")
            );

    @Autowired
    public Player2AuthenticationFilter(AuthenticationManagerImpl authenticationManager) {
        super(request, authenticationManager);

        setAuthenticationSuccessHandler((req, res, auth) -> {});

    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        Authentication toReturn = tryAuthenticateParameters(request, response);
        if (toReturn != null) return toReturn;

        return tryAuthenticateCookies(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request,response);
    }

    /*****************************************************************************************************
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     ******************************************************************************************************/
    private Authentication tryAuthenticateCookies(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        Authentication toReturn = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length >= 2) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("access_token"))
                    .findFirst();
            Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("refresh_token"))
                    .findFirst();

            if (accessTokenCookie.isPresent() && refreshTokenCookie.isPresent()) {
                toReturn = getAuthenticationManager().authenticate(
                        new JWTAuthenticationToken(
                                new JWTToken(accessTokenCookie.get().getValue()),
                                new JWTToken(refreshTokenCookie.get().getValue())
                        )
                );

                if (toReturn instanceof JWTAuthenticationToken token) {
                    response.addCookie(new Cookie("access_token", token.getAccessToken().getToken()));
                    response.addCookie(new Cookie("refresh_token", token.getRefreshToken().getToken()));
                }

            } //if (accessTokenCookie.isPresent() && refreshTokenCookie.isPresent())
        } //if (cookies != null && cookies.length >= 2)/

        return toReturn;
    }

    /********************************************************************************************************
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     ******************************************************************************************************/
    private Authentication tryAuthenticateParameters(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        try {

            Authentication toReturn;
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if(username == null || username.equals("") || password == null || password.equals("")){
                return null;
            }

            var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            toReturn = getAuthenticationManager().authenticate(authenticationToken);

            JWTToken access_token = new JWTToken(
                    toReturn.getName(),
                    "",
                    (List<GrantedAuthority>) toReturn.getAuthorities(),
                    JWTToken.JWT_ACCESS_TOKEN_LIFETIME
            );

            JWTToken refresh_token = new JWTToken(
                    toReturn.getName(),
                    "",
                    (List<GrantedAuthority>) toReturn.getAuthorities(),
                    JWTToken.JWT_REFRESH_TOKEN_LIFETIME
            );


            response.addCookie(new Cookie("access_token", access_token.getToken()));
            response.addCookie(new Cookie("refresh_token", refresh_token.getToken()));

            return toReturn;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}

