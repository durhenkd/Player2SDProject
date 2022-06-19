package com.player2.server.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/api/clique")
public class CliqueController {

    @GetMapping(path = "test")
    public ResponseEntity<String> testClique(){
        return ResponseEntity.ok().body("You can access /api/clique");
    }

}
