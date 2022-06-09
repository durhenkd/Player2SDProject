//package com.player2.server.security;
//
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.function.Supplier;
//
//@Component
//public class Player2AuthorizationManager implements AuthorizationManager<HttpServletRequest> {
//
//    @Override
//    public void verify(Supplier<Authentication> authentication, HttpServletRequest object) {
//        AuthorizationManager.super.verify(authentication, object);
//    }
//
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest object) {
//        return null;
//    }
//
//}
