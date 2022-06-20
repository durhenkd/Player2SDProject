package com.player2.server.web;

import com.player2.server.bussiness.PlayerService;
import com.player2.server.model.Clique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/api/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path = "test")
    public ResponseEntity<String> testPlayer() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(name + " can access /api/player");
    }

    @GetMapping(path = "clique")
    public ResponseEntity<List<Clique>> getAllCliques() {
        return ResponseEntity.ok(playerService.getCliques());
    }

    @GetMapping(path = "clique/{id}/posts")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(@PathVariable("id") int id) {
        return ResponseEntity.ok(playerService.getCliquePosts(id));
    }

    @GetMapping(path = "clique/{id}/posts/{post_id}")
    public ResponseEntity<String> getPost(@PathVariable("id") int id, @PathVariable("post_id") int post_id) {
        return ResponseEntity.ok(playerService.getCliquePost(id, post_id));
    }

    @GetMapping("feed")
    public ResponseEntity<List<PostResponseDTO>> getFeed() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(playerService.getFeed(name));
    }

    @PostMapping(path = "clique/{id}")
    public ResponseEntity<?> follow(@PathVariable("id") int id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        playerService.followClique(name, id);
        return ResponseEntity.ok(null);
    }

}
