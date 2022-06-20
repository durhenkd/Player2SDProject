package com.player2.server.web;

import com.player2.server.model.Player;
import lombok.Value;

@Value
public class MatchReponseDTO {

    public MatchReponseDTO(Player player, String postIt, int match_id) {
        this.username = player.getAccount().getUsername();
        this.bio = player.getBio();
        this.player_id = player.getAccount().getId().intValue();
        this.postIt = postIt;
        this.match_id = match_id;
        this.gender = player.getGender().toString();
    }

    String username;
    String bio;
    String postIt;
    int player_id;
    int match_id;
    String gender;

}
