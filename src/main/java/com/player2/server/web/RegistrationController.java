package com.player2.server.web;

import com.player2.server.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @PostMapping(path = "player")
    public ResponseEntity<Player> registerPlayer(@RequestBody PlayerRegistrationDTO registrationDTO){
        log.info("Registering: " + registrationDTO.toString());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping(path = "clique")
    public ResponseEntity<Player> registerClique(@RequestBody CliqueRegistrationDTO registrationDTO){
        log.info("Registering: " + registrationDTO.toString());
        return ResponseEntity.ok().body(null);
    }

}
