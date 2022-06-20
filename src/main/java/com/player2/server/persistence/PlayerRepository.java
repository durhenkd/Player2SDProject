package com.player2.server.persistence;

import com.player2.server.model.Account;
import com.player2.server.model.Clique;
import com.player2.server.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByAccount(Account account);
    boolean existsByAccount(Account account);
    Optional<Player> findByAccount_Username(String username);

}
