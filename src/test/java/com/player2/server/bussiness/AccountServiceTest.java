package com.player2.server.bussiness;

import com.player2.server.persistence.AccountRepository;
import com.player2.server.persistence.CategoryRepository;
import com.player2.server.persistence.CliqueRepository;
import com.player2.server.persistence.PlayerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock private AccountRepository accountRepository;
    @Mock private PlayerRepository playerRepository;
    @Mock private CliqueRepository cliqueRepository;
    @Mock private CategoryRepository categoryRepository;

//    private AccountService accountService;
//
//    @BeforeEach
//    void setUp(){
//        accountService = new AccountService(accountRepository,playerRepository,cliqueRepository,categoryRepository);
//    }

}