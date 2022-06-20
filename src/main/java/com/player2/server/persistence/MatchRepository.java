package com.player2.server.persistence;

import com.player2.server.model.Match;
import com.player2.server.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findAllByPlayer1OrPlayer2(Player player1, Player player2);

}
