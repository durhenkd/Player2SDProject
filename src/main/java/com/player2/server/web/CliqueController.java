package com.player2.server.web;

import com.player2.server.bussiness.CliqueService;
import com.player2.server.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/api/clique")
public class CliqueController {

    private final CliqueService cliqueService;

    @Autowired
    public CliqueController(CliqueService cliqueService) {
        this.cliqueService = cliqueService;
    }

    @GetMapping(path = "test")
    public ResponseEntity<String> testClique(){
        return ResponseEntity.ok().body("You can access /api/clique");
    }

    @PostMapping(path = "post")
    public ResponseEntity<?> addPost(@RequestBody PostSubmitDTO dto){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        cliqueService.addPost(name, dto);
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "post")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(cliqueService.getAllPosts(name));
    }

    @GetMapping(path = "post/{id}")
    public ResponseEntity<String> getPost(@PathVariable("id") int id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(cliqueService.getPost(name, id));
    }

}
