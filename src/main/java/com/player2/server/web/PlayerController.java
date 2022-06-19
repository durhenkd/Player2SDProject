package com.player2.server.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/api/player")
public class PlayerController {

    @GetMapping(path = "test")
    public ResponseEntity<String> testPlayer(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(name + " can access /api/player");
    }

}
