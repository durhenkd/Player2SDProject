package com.player2.server.web;

import com.player2.server.bussiness.AccountService;
import com.player2.server.exception.AlreadyExistsException;
import com.player2.server.exception.InvalidRegisterInputException;
import com.player2.server.model.Clique;
import com.player2.server.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/api")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "register/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("You can access /api/registration");
    }

    @PostMapping(path = "register/player")
    public ResponseEntity<LoginInformationDTO> registerPlayer(@RequestBody PlayerRegistrationDTO registrationDTO) {
        log.info("Registering: " + registrationDTO.toString());
        try {
            Player player = accountService.save(registrationDTO);
            return ResponseEntity.ok().body(
                    new LoginInformationDTO(
                            player.getAccount().getId().intValue(),
                            player.getAccount().getUsername(),
                            false)
            );
        } catch (AlreadyExistsException | InvalidRegisterInputException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping(path = "register/clique")
    public ResponseEntity<LoginInformationDTO> registerClique(@RequestBody CliqueRegistrationDTO registrationDTO) {
        log.info("Registering: " + registrationDTO.toString());
        try {
            Clique clique = accountService.save(registrationDTO);
            return ResponseEntity.ok().body(
                    new LoginInformationDTO(
                            clique.getAccount().getId().intValue(),
                            clique.getAccount().getUsername(),
                            true)
            );
        } catch (AlreadyExistsException | InvalidRegisterInputException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping(path = "login")
    public ResponseEntity<LoginInformationDTO> login() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(accountService.getLoginInformation(name));
    }

    @GetMapping(path = "test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok().body("You can access /api/test");
    }


}
