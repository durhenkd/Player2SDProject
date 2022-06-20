package com.player2.server.bussiness;

import com.player2.server.model.Clique;
import com.player2.server.model.Post;
import com.player2.server.persistence.CliqueRepository;
import com.player2.server.persistence.PostRepository;
import com.player2.server.web.PostResponseDTO;
import com.player2.server.web.PostSubmitDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CliqueService {

    private static final String POST_FOLDER = "clique_posts";

    private final PostRepository postRepository;
    private final CliqueRepository cliqueRepository;

    @Autowired
    public CliqueService(PostRepository postRepository, CliqueRepository cliqueRepository) {
        this.postRepository = postRepository;
        this.cliqueRepository = cliqueRepository;
    }

    /**
     * TODO: this
     * @param username
     * @param dto
     */
    public void addPost(String username, PostSubmitDTO dto){
        Optional<Clique> maybeClique = cliqueRepository.findByAccount_Username(username);
        if (maybeClique.isEmpty()) {
            log.error("addPost: Account " + username + " does not exist");
            return;
        }
        Clique clique = maybeClique.get();

        if(clique.getPosts().stream().anyMatch((post)-> post.getTitle().equals(dto.getTitle()))){
            log.error("addPost: post with name " + dto.getTitle() + " already exists");
            return;
        }

        File folder = new File(POST_FOLDER + "/" + username);
        if(!folder.exists()) folder.mkdirs();

        String pathname = POST_FOLDER + "/" + username + "/" + dto.getTitle() + ".txt";
        File f = new File(pathname);

        if(!f.exists()){
            try {
                f.createNewFile();
                log.info("addPost: Created new post file at path: " + pathname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter fw = new FileWriter(f)) {
            fw.write(dto.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Post post = new Post(
                Date.from(Instant.now()).toString(),
                pathname,
                dto.getTitle()
                );

        post = postRepository.save(post);
        clique.getPosts().add(post);
        cliqueRepository.save(clique);
        log.info("addPost: Updated database");
    }

    /**
     *
     * @param username
     * @return
     */
    public List<PostResponseDTO> getAllPosts(String username){
        Optional<Clique> maybeClique = cliqueRepository.findByAccount_Username(username);
        if (maybeClique.isEmpty()) {
            log.error("getAllPosts: Account " + username + " does not exist");
            return new ArrayList<>();
        }
        Clique clique = maybeClique.get();
        log.info("getAllPosts: return all posts for: " + username);
        return clique.getPosts()
                .stream()
                .map(e -> new PostResponseDTO(e,clique.getName(),clique.getId().intValue()))
                .collect(Collectors.toList());
    }

    public String getPost(String username, int id){
        Optional<Clique> maybeClique = cliqueRepository.findByAccount_Username(username);
        if (maybeClique.isEmpty()) {
            log.error("getAllPosts: Account " + username + " does not exist");
            return null;
        }
        log.info("getAllPosts: return all posts for: " + username);
        Optional<Post> maybePost = maybeClique.get().getPosts().stream().filter((p) -> p.getId() == id).findFirst();

        return PlayerService.getFileContent(maybePost);
    }
}
