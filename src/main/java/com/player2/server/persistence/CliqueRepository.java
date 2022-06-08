package com.player2.server.persistence;

import com.player2.server.model.Account;
import com.player2.server.model.Clique;
import com.player2.server.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CliqueRepository extends JpaRepository<Clique, Long> {

    Optional<Clique> findByAccount(Account account);

}
