package com.player2.server.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @GetMapping(path = "test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("You are a player!");
    }

}
