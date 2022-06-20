package com.player2.server.bussiness;

import com.player2.server.model.Clique;
import com.player2.server.model.Match;
import com.player2.server.model.Player;
import com.player2.server.model.Post;
import com.player2.server.persistence.CliqueRepository;
import com.player2.server.persistence.MatchRepository;
import com.player2.server.persistence.PlayerRepository;
import com.player2.server.web.MatchReponseDTO;
import com.player2.server.web.PostResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final CliqueRepository cliqueRepository;
    private final MatchRepository  matchRepository;

    @Autowired
    public PlayerService(
            PlayerRepository playerRepository,
            CliqueRepository cliqueRepository,
            MatchRepository matchRepository
    ) {
        this.playerRepository = playerRepository;
        this.cliqueRepository = cliqueRepository;
        this.matchRepository = matchRepository;
    }

    /** This function searches for the contents of the post in the file system of the server
     * @param maybePost The post
     * @return null if the contents can't be found or the post doesn't exist, the c
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

    /**
     *
     * @param id - of the clique object
     * @return all the information of the posts (not the contents)
     * @see PostResponseDTO
     */
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
     * @param id - of the clique
     * @param post_id - if of the post
     * @return the contents of the post with id post_id of the clique with id id
     */
    public String getCliquePost(int id, int post_id) {
        Optional<Post> maybePost = cliqueRepository
                .findById((long) id)
                .get()
                .getPosts()
                .stream()
                .filter(p -> p.getId() == post_id)
                .findFirst();

        log.info("getCliquePost: found post");
        return getFileContent(maybePost);
    }

    /** returns the most recent post of each followed clique
     * @param name the username of the player
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

        log.info("getFeed: done");
        return posts;
    }

    /**
     * @return all the cliques
     */
    public List<Clique> getCliques() {
        return cliqueRepository.findAll();
    }

    /** this functions adds the clique with the given id to the list of followed cliques of the user
     * @param name the username of the player
     * @param id  the id of the clique
     */
    public void followClique(String name, int id) {
        Clique clique = cliqueRepository.findById((long) id).get();
        Player player = playerRepository.findByAccount_Username(name).get();

        if (player.getFollows().stream().noneMatch(clique1 -> clique.getId().equals(clique1.getId()))) {
            player.getFollows().add(clique);
            playerRepository.save(player);
        }
        log.info("addFollow: added " + clique.getName() + " to " + player.getAccount().getUsername() + "'s follows");
    }

    /**
     *
     * @param name - the username of the user
     * @return a list with all the matches of the user
     */
    public List<MatchReponseDTO> getMatches(String name) {
        Player player = playerRepository.findByAccount_Username(name).get();
        return matchRepository.findAllByPlayer1OrPlayer2(player, player)
                .stream()
                .filter(p -> (p.getAccepted1() + p.getAccepted2()) == 2)
                .map(p -> {
                    if (!Objects.equals(p.getPlayer1().getAccount().getId(), player.getAccount().getId()))
                        return new MatchReponseDTO(p.getPlayer1(), p.getPostIt1(), p.getId().intValue());
                    else
                        return new MatchReponseDTO(p.getPlayer2(), p.getPostIt2(), p.getId().intValue());
                })
                .collect(Collectors.toList());
    }

    /**
     *
     * @param name - the username of the user
     * @return the next potential match for the user, if it exists
     */
    public Optional<MatchReponseDTO> getPotentialMatch(String name){
        Player player = playerRepository.findByAccount_Username(name).get();
        return matchRepository.findAllByPlayer1OrPlayer2(player, player)
                .stream()
                .filter(p -> {

                    if(p.getAccepted1() == -1 || p.getAccepted2() == -1)
                        return false;

                    if(p.getPlayer1().equals(player) && p.getAccepted1() == 1)
                        return false;
                    else if(p.getPlayer2().equals(player) && p.getAccepted2() == 1)
                        return false;
                    return ((p.getAccepted1() + p.getAccepted2()) >= 0 && (p.getAccepted1() + p.getAccepted2()) <= 1);
                })
                .max((m1, m2) -> Long.compare(m1.getScore(), m2.getScore()))
                .map(p -> {
                    if (!Objects.equals(p.getPlayer1().getAccount().getId(), player.getAccount().getId()))
                        return new MatchReponseDTO(p.getPlayer1(), p.getPostIt1(), p.getId().intValue());
                    else
                        return new MatchReponseDTO(p.getPlayer2(), p.getPostIt2(), p.getId().intValue());
                })
                ;
    }

    /**
     * Account with username name accepts the match with id match_id, leaving the message postIt
     * @param name
     * @param match_id
     * @param postIt
     */
    public void acceptMatch(String name, int match_id, String postIt){
        handleMatch(name, match_id, postIt, 1);
    }

    public void refuseMatch(String name, int match_id, String postIt) {
       handleMatch(name, match_id, postIt, -1);
    }

    private void handleMatch(String name, int match_id, String postIt, int accepted){
        Match match = matchRepository.findById((long) match_id).get();
        if (match.getPlayer1().getAccount().getUsername().equals(name)){
            match.setAccepted1(accepted);
            match.setPostIt1(postIt);
        } else{
            match.setAccepted2(accepted);
            match.setPostIt2(postIt);
        }
        matchRepository.save(match);
    }
}
