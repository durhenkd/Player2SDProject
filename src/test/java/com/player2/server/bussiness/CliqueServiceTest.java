package com.player2.server.bussiness;

import com.player2.server.persistence.CliqueRepository;
import com.player2.server.persistence.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CliqueServiceTest {

    @Mock private PostRepository postRepository;
    @Mock private CliqueRepository cliqueRepository;

    private CliqueService cliqueService;

    @BeforeEach
    void setUp(){
        cliqueService = new CliqueService(postRepository, cliqueRepository);
    }

//    @Test
//    void addPost() {
//
//    }
}