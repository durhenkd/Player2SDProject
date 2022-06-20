package com.player2.server.bussiness;

import com.player2.server.model.Clique;
import com.player2.server.model.Player;
import com.player2.server.model.Post;
import com.player2.server.persistence.CliqueRepository;
import com.player2.server.persistence.PlayerRepository;
import com.player2.server.web.PostResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final CliqueRepository cliqueRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, CliqueRepository cliqueRepository) {
        this.playerRepository = playerRepository;
        this.cliqueRepository = cliqueRepository;
    }

    /**
     * @param maybePost
     * @return
     */
    static String getFileContent(Optional<Post> maybePost) {
        if (maybePost.isEmpty()) {
            log.error("getAllPosts: Post does not exist");
            return null;
        }
        File f = new File(maybePost.get().getContentPath());
        try (FileReader fr = new FileReader(f)) {
            StringBuilder sb = new StringBuilder();
            int character = fr.read();
            while (character != -1) {
                sb.append((char) character);
                character = fr.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PostResponseDTO> getCliquePosts(int id) {
        Clique clique =  cliqueRepository
                .findById(Long.valueOf(id))
                .get();
        return clique.getPosts()
                .stream()
                .map(e -> new PostResponseDTO(e,clique.getName(),clique.getId().intValue()))
                .collect(Collectors.toList());
    }

    /**
     * @param id
     * @param post_id
     * @return
     */
    public String getCliquePost(int id, int post_id) {
        Optional<Post> maybePost = cliqueRepository
                .findById(Long.valueOf(id))
                .get()
                .getPosts()
                .stream()
                .filter(p -> p.getId() == post_id)
                .findFirst();

        return getFileContent(maybePost);
    }

    /**
     * @param name
     * @return
     */
    public List<PostResponseDTO> getFeed(String name) {
        Player player = playerRepository.findByAccount_Username(name).get();

        ArrayList<PostResponseDTO> posts = new ArrayList<>();
        for (Clique c : player.getFollows()) {
            if (c.getPosts().size() == 0) continue;
            Post post = c.getPosts().get(c.getPosts().size() - 1);

            posts.add(new PostResponseDTO(post, c.getName(), c.getId().intValue()));
        }

        return posts;
    }

    /**
     * @return
     */
    public List<Clique> getCliques() {
        return cliqueRepository.findAll();
    }

    /**
     * @param name
     * @param id
     */
    public void followClique(String name, int id) {
        Clique clique = cliqueRepository.findById(Long.valueOf(id)).get();
        Player player = playerRepository.findByAccount_Username(name).get();

        if (!player.getFollows().stream().anyMatch(clique1 -> clique.getId().equals(clique1.getId()))) {
            player.getFollows().add(clique);
            playerRepository.save(player);
        }
    }
}
