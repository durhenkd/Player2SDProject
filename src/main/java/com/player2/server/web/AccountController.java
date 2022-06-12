package com.player2.server.web;

import com.player2.server.bussiness.AccountService;
import com.player2.server.exception.AlreadyExistsException;
import com.player2.server.exception.InvalidRegisterInputException;
import com.player2.server.model.Clique;
import com.player2.server.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/api/register/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("You can access /api/registration");
    }

    @PostMapping(path = "/api/register/player")
    public ResponseEntity<Player> registerPlayer(@RequestBody PlayerRegistrationDTO registrationDTO){
        log.info("Registering: " + registrationDTO.toString());
        try{
            return ResponseEntity.ok().body(accountService.save(registrationDTO));
        } catch (AlreadyExistsException | InvalidRegisterInputException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping(path = "/api/register/clique")
    public ResponseEntity<Clique> registerClique(@RequestBody CliqueRegistrationDTO registrationDTO) {
        log.info("Registering: " + registrationDTO.toString());
        try {
            return ResponseEntity.ok().body(accountService.save(registrationDTO));
        } catch (AlreadyExistsException | InvalidRegisterInputException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping(path = "/api/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("You logged in!");
    }

    @GetMapping(path = "/api/test")
    public ResponseEntity<String> testApi(){
        return ResponseEntity.ok().body("You can access /api/test");
    }


    @GetMapping(path = "/api/player/test")
    public ResponseEntity<String> testPlayer(){
        return ResponseEntity.ok().body("You can access /api/player");
    }

    @GetMapping(path = "/api/clique/test")
    public ResponseEntity<String> testClique(){
        return ResponseEntity.ok().body("You can access /api/clique");
    }

}
